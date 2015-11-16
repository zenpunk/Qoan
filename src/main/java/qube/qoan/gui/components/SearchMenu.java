package qube.qoan.gui.components;

import com.google.inject.Guice;
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
import qube.qai.main.QaiModule;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.UUIDServiceInterface;
import qube.qoan.gui.interfaces.SearchAgent;
import qube.qoan.services.QoanModule;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rainbird on 10/31/15.
 */
public class SearchMenu extends Panel implements SearchAgent {

    @Inject @Named("Wiktionary_en")
    private SearchServiceInterface wiktionarySearchService;

    @Inject @Named("Wikipedia_en")
    private SearchServiceInterface wikipediaSearchService;

    @Inject
    private UUIDServiceInterface uuidService;

    private VerticalLayout layout;

    private WorkSpace workSpace;

    private Table resultTable;

    public SearchMenu() {
        super();

        this.workSpace = workSpace;

        // i think this is more or less what we will need to do in order to make the injector to work
        Injector injector = Guice.createInjector(new QoanModule(), new QaiModule());
        injector.injectMembers(this);
        // do the initialization
        initialize();
    }

    private void initialize() {
        // begin with setting the size
        setWidth("300");
        setHeight("550");

        layout = new VerticalLayout();

        // some dummy label do fill in the display
//        Label label = new Label("Display something as SearchMenu");
//        parentLayout.addComponent(label);
        SearchPanel searchPanel = new SearchPanel(this);
        layout.addComponent(searchPanel);

        resultTable = new Table("Results:");
        resultTable.setDescription("drag results to workspace to visualize their contents");
        resultTable.addContainerProperty("Source", String.class, null);
        resultTable.addContainerProperty("Title", String.class, null);
        resultTable.setVisible(false);
        resultTable.setSelectable(true);
        resultTable.setImmediate(true);
        //resultTable.setColumnReorderingAllowed(true);
        resultTable.setPageLength(7);
        resultTable.setDragMode(Table.TableDragMode.ROW);

        // add drag-n-drop support
        //workSpace.setDropHandler();

        layout.addComponent(resultTable);

        setContent(layout);
    }

    /**
     * creates a drag-n-drop handler which would handle the added table rows
     * @return
     */
    public DropHandler createDropHandler(AbsoluteLayout parentLayout) {
        DropHandler dropHandler = new MoveHandler(parentLayout);

        return dropHandler;
    }

    // Handles drops both on an AbsoluteLayout and
    // on components contained within it
    class MoveHandler implements DropHandler {

        AbsoluteLayout parentLayout;

        public MoveHandler(AbsoluteLayout layout) {
            this.parentLayout = layout;
        }

        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }

        public void drop(DragAndDropEvent event) {

            Transferable transferable = event.getTransferable();

            // check what source we are receiving events from
            if (transferable instanceof DragAndDropWrapper.WrapperTransferable) {

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
                pos.setLeftValue(pos.getLeftValue() + xChange);
                pos.setTopValue(pos.getTopValue() + yChange);

            } else if (transferable instanceof DataBoundTransferable) {

                DataBoundTransferable t = (DataBoundTransferable) event.getTransferable();
                Object itemId = t.getItemId();
                Item item = resultTable.getItem(itemId);
                String title = (String) item.getItemProperty("Title").getValue();
                String source = (String) item.getItemProperty("Source").getValue();

                WikiArticle wikiArticle = null;
                if ("Wikipedia".equalsIgnoreCase(source)) {
                    wikiArticle = wikipediaSearchService.retrieveDocumentContentFromZipFile(title);
                } else if ("Wiktionary".equalsIgnoreCase(source)) {
                    wikiArticle = wiktionarySearchService.retrieveDocumentContentFromZipFile(title);
                }

                if (wikiArticle == null) {
                    return;
                }

                wikiArticle.setSource(source);
                wikiArticle.setId(uuidService.createUUIDString());

                WikiArticleTag wikiTag = new WikiArticleTag(source, wikiArticle);
                DragAndDropWrapper wikiTagWrapper = new DragAndDropWrapper(wikiTag);
                wikiTagWrapper.setSizeUndefined();
                wikiTagWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);

                // @TODO add logic for placing the component where  it is created
                parentLayout.addComponent(wikiTagWrapper, "left: 50px; top: 50px;");

                Notification.show("WikiArricle: " + title + " added to Workpsace");
            }

        }
    }

    @Override
    public void searchFor(String source, String searchTerm, String searchIn, int maxResults) {
        // for the time being simply display search terms
        String message = "Search from: " + source + " in: " + searchIn + " for: " + searchTerm; // + " with: " + maxHits + " max. results";

        Notification.show(message);

        Collection<String> results = new ArrayList<String>();
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

    private void addRowsToTable(String source, Collection<String> results) {
        if (results != null && !results.isEmpty()) {
            // make the table visible and clear contents before adding new items
            resultTable.setVisible(true);
            resultTable.removeAllItems();

            for (String result : results) {
                Object itemId = resultTable.addItem();
                Item row = resultTable.getItem(itemId);
                row.getItemProperty("Source").setValue(source);
                row.getItemProperty("Title").setValue(result);

            }
        }
    }
}
