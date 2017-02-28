/*
 * Copyright 2017 Qoan Software Association. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package qube.qoan.gui.components.workspace.finance;


import com.google.inject.Injector;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.vaadin.ui.*;
import qube.qai.persistence.WikiArticle;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.workspace.finance.parser.WikiIntegrationUtils;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/28/15.
 */
public class FinanceListing extends Panel {


    @Inject //@Named("HAZELCAST_CLIENT")
    private HazelcastInstance hazelcastInstance;

    private Accordion listingsAccordion;

    public FinanceListing() {
        super();
        initialize();
    }

    private void initialize() {

        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
        injector.injectMembers(this);

        VerticalLayout layout = new VerticalLayout();

        listingsAccordion = new Accordion();
        listingsAccordion.setSizeFull();
        layout.addComponent(listingsAccordion);

        setContent(layout);
    }

    public void displayListing(String listingName) {

        IMap<String, WikiArticle> wikiMap = hazelcastInstance.getMap("WIKIPEDIA_EN");
        WikiArticle article = wikiMap.get(listingName);

        WikiIntegrationUtils wikiIntegration = new WikiIntegrationUtils(hazelcastInstance);
        Table entitiesTable = wikiIntegration.convertHtmlTable(article);

        // check whether the stock-entities which are in the listing
        // have been added to hazelcast and if that is not the
        // case add them
        //entitiesTable.

        // create and add the panel to on screen
        Panel content = new Panel(entitiesTable);
        listingsAccordion.addTab(content).setCaption(listingName);
    }
}
