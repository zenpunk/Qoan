package qube.qoan.gui.components;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.vaadin.data.Item;
import com.vaadin.ui.*;
import qube.qai.main.QaiModule;
import qube.qai.services.SearchServiceInterface;
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

    private VerticalLayout layout;

    private Table resultTable;

    public SearchMenu() {
        super();

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

        resultTable = new Table("Results");
        resultTable.addContainerProperty("Source", String.class, null);
        resultTable.addContainerProperty("Title", String.class, null);
        resultTable.setVisible(false);
        resultTable.setPageLength(5);

        layout.addComponent(resultTable);

        setContent(layout);
    }

    @Override
    public void searchFor(String source, String searchTerm, String searchIn, int maxResults) {
        // for the time being simply display search terms
        String message = "Search from: " + source + " in: " + searchIn + " for: " + searchTerm; // + " with: " + maxHits + " max. results";

        Notification.show(message);

        Collection<String> results = searchService.searchInputString(searchTerm, searchIn, maxResults);
        if (results != null && !results.isEmpty()) {
            resultTable.setVisible(true);
            resultTable.clear();
            for (String result : results) {
                Object itemId = resultTable.addItem();
                Item row = resultTable.getItem(itemId);
                row.getItemProperty("Source").setValue(source);
                row.getItemProperty("Title").setValue(result);
            }
        }
    }
}
