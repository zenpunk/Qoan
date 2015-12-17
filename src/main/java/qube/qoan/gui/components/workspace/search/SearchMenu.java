package qube.qoan.gui.components.workspace.search;

import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.vaadin.data.Item;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.*;
import qube.qai.persistence.WikiArticle;
import qube.qai.procedure.Procedure;
import qube.qai.services.ProcedureSource;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.UUIDServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.workspace.WorkSpace;
import qube.qoan.gui.components.workspace.procedure.ProcedureListPanel;
import qube.qoan.gui.components.workspace.procedure.ProcedureTag;
import qube.qoan.gui.components.workspace.wiki.WikiArticleTag;
import qube.qoan.gui.interfaces.SearchAgent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rainbird on 10/31/15.
 */
public class SearchMenu extends Panel implements SearchAgent {

    private boolean debug = true;

    @Inject @Named("Wiktionary_en")
    private SearchServiceInterface wiktionarySearchService;

    @Inject @Named("Wikipedia_en")
    private SearchServiceInterface wikipediaSearchService;

    @Inject
    private ProcedureSource procedureSource;

    @Inject
    private UUIDServiceInterface uuidService;

    private VerticalLayout layout;

    private WorkSpace workSpace;

    private Table resultTable;

    public SearchMenu() {
        super();

        // start with self-inoculation
        Injector injector = QoanUI.getInjector();
        injector.injectMembers(this);

        initialize();
    }

    private void initialize() {

        layout = new VerticalLayout();
        layout.setWidth("350px");
        // some dummy label do fill in the display
        SearchPanel searchPanel = new SearchPanel(this);
        layout.addComponent(searchPanel);

        createResultTable();
        layout.addComponent(resultTable);

        setContent(layout);
    }

    private void createResultTable() {

        resultTable = new Table("Results:");
        //resultTable.setDescription("drag results to workspace to visualize their contents");
        resultTable.addContainerProperty("Source", String.class, null);
        resultTable.addContainerProperty("Title", String.class, null);
        resultTable.addContainerProperty("Relevance", Double.class, null);
        resultTable.addContainerProperty("File", String.class, null);

        resultTable.setVisible(false);
        resultTable.setSelectable(true);
        resultTable.setImmediate(true);
        //resultTable.setColumnReorderingAllowed(true);
        resultTable.setPageLength(5);
        resultTable.setDragMode(Table.TableDragMode.ROW);
        resultTable.setColumnReorderingAllowed(true);
        resultTable.setSizeUndefined();
        resultTable.setColumnCollapsingAllowed(true);
        resultTable.setFooterVisible(true);
        resultTable.setSortAscending(true);

        // disable these columns initially so that they don't take too much space
        resultTable.setColumnCollapsed("Source", true);
        resultTable.setColumnCollapsed("File", true);
    }

    /**
     * creates a drag-n-drop handler which would handle the added table rows
     * @return
     */
    public DropHandler createDropHandler(AbsoluteLayout parentLayout) {
        DropHandler dropHandler = new MoveAndDropHandler(parentLayout);

        return dropHandler;
    }

    /**
     * does the actual searching in the source-index
     * @param source
     * @param searchTerm
     * @param searchIn
     * @param maxResults
     */
    @Override
    public void searchFor(String source, String searchTerm, String searchIn, int maxResults) {
        // for the time being simply display search terms
        String message = "Search from: " + source + " in: " + searchIn + " for: " + searchTerm; // + " with: " + maxHits + " max. results";

        Notification.show(message);

        Collection<SearchResult> results = new ArrayList<SearchResult>();
        if ("Wikipedia".equalsIgnoreCase(source)) {
            results.addAll(wikipediaSearchService.searchInputString(searchTerm, searchIn, maxResults));
            addRowsToTable(source, results);
        } else if ("Wiktionary".equalsIgnoreCase(source)) {
            results.addAll(wiktionarySearchService.searchInputString(searchTerm, searchIn, maxResults));
            addRowsToTable(source, results);
        } else if ("Wikipedia & Wiktionary".equalsIgnoreCase(source)) {
            results.addAll(wikipediaSearchService.searchInputString(searchTerm, searchIn, maxResults));
            addRowsToTable("Wikipedia", results);
            results.clear();
            results.addAll(wiktionarySearchService.searchInputString(searchTerm, searchIn, maxResults));
            addRowsToTable("Wiktionary", results);
        }


    }

    private void addRowsToTable(String source, Collection<SearchResult> results) {

        if (results != null && !results.isEmpty()) {
            // make the table visible and clear contents before adding new items
            resultTable.setCaption("results: (" + results.size() + ")");
            resultTable.setVisible(true);
            resultTable.removeAllItems();

            for (SearchResult result : results) {
                Object itemId = resultTable.addItem();
                Item row = resultTable.getItem(itemId);
                row.getItemProperty("Source").setValue(source);
                row.getItemProperty("Title").setValue(result.getTitle());
                row.getItemProperty("Relevance").setValue(result.getRelevance());
                row.getItemProperty("File").setValue(result.getFilename());

            }
        }
    }

    /**
     * @TODO use as item-id class for searches so that the event doesn't need reference to the table
     */
    class SearchMenuItem {

    }

    /**
     * @TODO can this class be made to be independent?
     * Handles drops both on an AbsoluteLayout and
     * on components contained within it
     */
    class MoveAndDropHandler implements DropHandler {

        int left = 40;
        int top = 40;

        AbsoluteLayout parentLayout;

        public MoveAndDropHandler(AbsoluteLayout layout) {
            this.parentLayout = layout;
        }

        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }

        public void drop(DragAndDropEvent event) {

            Transferable transferable = event.getTransferable();

            // check what source we are receiving events from
            if (transferable instanceof DragAndDropWrapper.WrapperTransferable) {
                handleDragEvent(event);
            } else if (transferable instanceof DataBoundTransferable) {
                handleDropEvent((DataBoundTransferable) transferable);
            }

        }

        /**
         * this handles the dragging on the layout itself
         * and changes the positions of the components
         * which are being dragged around
         * @TODO extend this to work selectors
         * @param transferable
         */
        private void handleDropEvent(DataBoundTransferable transferable) {

            Object itemId = transferable.getItemId();
            DragAndDropWrapper tagWrapper = null;

            if (itemId instanceof ProcedureListPanel.ProcedureItem) {

                ProcedureListPanel.ProcedureItem pItem = (ProcedureListPanel.ProcedureItem) itemId;
                Procedure procedure = procedureSource.getProcedureWithName(pItem.getName());
                ProcedureTag procedureTag = new ProcedureTag(procedure, parentLayout);
                tagWrapper = new DragAndDropWrapper(procedureTag);

            } else {

                Item item = resultTable.getItem(itemId);
                String file = (String) item.getItemProperty("File").getValue();
                String source = (String) item.getItemProperty("Source").getValue();

                WikiArticle wikiArticle = null;
                if ("Wikipedia".equalsIgnoreCase(source)) {
                    wikiArticle = wikipediaSearchService.retrieveDocumentContentFromZipFile(file);
                } else if ("Wiktionary".equalsIgnoreCase(source)) {
                    wikiArticle = wiktionarySearchService.retrieveDocumentContentFromZipFile(file);
                }

                if (wikiArticle == null) {
                    return;
                }

                wikiArticle.setSource(source);
                wikiArticle.setId(uuidService.createUUIDString());

                WikiArticleTag wikiTag = new WikiArticleTag(source, wikiArticle, parentLayout);
                tagWrapper = new DragAndDropWrapper(wikiTag);
            }

            tagWrapper.setSizeUndefined();
            tagWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);

            // try to find out the component position to enter
            String positionString;
            left = left + 5;
            top = top + 5;
            positionString = "left: " + left + "px; top: " + top + "px;";
            parentLayout.addComponent(tagWrapper, positionString);

        }

        /**
         * this handles the drop events from external sources
         * in this case, drops from search-menu result-table on workspace
         * @param event
         */
        private void handleDragEvent(DragAndDropEvent event) {
            DragAndDropWrapper.WrapperTransferable t =
                    (DragAndDropWrapper.WrapperTransferable) event.getTransferable();
            DragAndDropWrapper.WrapperTargetDetails details =
                    (DragAndDropWrapper.WrapperTargetDetails) event.getTargetDetails();

            // Calculate the drag coordinate difference
            int xChange = details.getMouseEvent().getClientX()
                    - t.getMouseDownEvent().getClientX();
            int yChange = details.getMouseEvent().getClientY()
                    - t.getMouseDownEvent().getClientY();

            // Move the component in the absolute parentLayout
            AbsoluteLayout.ComponentPosition pos = parentLayout.getPosition(t.getSourceComponent());
            // check to make sure there is indeed a position to be changed
            // can happen that something dragged on layout, but not part of the
            // layout is being dropped- we can't change positions of those, after all
            if (pos != null) {
                pos.setLeftValue(pos.getLeftValue() + xChange);
                pos.setTopValue(pos.getTopValue() + yChange);
            }
        }
    }
}
