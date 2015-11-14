package qube.qoan.gui.components;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.vaadin.data.Item;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.ui.*;
import qube.qai.main.QaiModule;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.UUIDServiceInterface;
import qube.qoan.gui.interfaces.SearchAgent;
import qube.qoan.services.QoanModule;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by rainbird on 10/31/15.
 */
public class SearchMenu extends Panel implements SearchAgent {

    @Inject @Named("Wiktionary_en")
    private SearchServiceInterface searchService;

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
//        layout.addComponent(label);
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
    public DropHandler createDropHandler() {
        DropHandler dropHandler = new DropHandler() {
            @Override
            public void drop(DragAndDropEvent dropEvent) {
                DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
                Object itemId = t.getItemId();
                Item item = resultTable.getItem(itemId);
                String title = (String) item.getItemProperty("Title").getValue();
                String source = (String) item.getItemProperty("Source").getValue();

                WikiArticle wikiArticle = searchService.retrieveDocumentContentFromZipFile(title);
                wikiArticle.setSource(source);
                wikiArticle.setId(uuidService.createUUIDString());

                WikiArticleTag wikiTag = new WikiArticleTag(wikiArticle);
                workSpace.addComponentToDisplay(wikiTag);

                Notification.show("WikiArricle: " + title + " added to Workpsace");
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return new And(new SourceIs(resultTable), AbstractSelect.AcceptItem.ALL);
            }
        };

        return dropHandler;
    }

    @Override
    public void searchFor(String source, String searchTerm, String searchIn, int maxResults) {
        // for the time being simply display search terms
        String message = "Search from: " + source + " in: " + searchIn + " for: " + searchTerm; // + " with: " + maxHits + " max. results";

        Notification.show(message);

        Collection<String> results = searchService.searchInputString(searchTerm, searchIn, maxResults);
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
