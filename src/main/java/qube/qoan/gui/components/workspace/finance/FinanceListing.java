package qube.qoan.gui.components.workspace.finance;


import com.google.inject.Injector;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.vaadin.ui.*;
import qube.qai.data.stores.DataStore;
import qube.qai.data.stores.StockEntityDataStore;
import qube.qai.parsers.WikiIntegration;
import qube.qai.persistence.StockEntity;
import qube.qai.persistence.WikiArticle;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.workspace.finance.parser.WikiIntegrationUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

/**
 * Created by rainbird on 12/28/15.
 */
public class FinanceListing extends Panel {


    @Inject
    private HazelcastInstance hazelcastInstance;

    private Accordion listingsAccordion;

    public FinanceListing() {
        super();
        initialize();
    }

    private void initialize() {

        Injector injector = ((QoanUI)UI.getCurrent()).getInjector();
        injector.injectMembers(this);

        VerticalLayout layout = new VerticalLayout();

        listingsAccordion = new Accordion();
        listingsAccordion.setSizeFull();
        layout.addComponent(listingsAccordion);

        setContent(layout);
    }

    public void displayListing(String listingName) {

        IMap<String,WikiArticle> stockMap = hazelcastInstance.getMap("WIKIPEDIA_EN");
        WikiArticle article = stockMap.get(listingName);

        WikiIntegrationUtils wikiIntegration = new WikiIntegrationUtils();
        Table entitiesTable = wikiIntegration.convertHtmlTable(article);

        Panel content = new Panel(entitiesTable);
        listingsAccordion.addTab(content).setCaption(listingName);
    }
}
