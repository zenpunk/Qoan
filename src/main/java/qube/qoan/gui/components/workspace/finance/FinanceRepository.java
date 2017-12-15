/*
 * Copyright 2017 Qoan Wissenschaft & Software. All rights reserved.
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
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qoan.services.QoanInjectorService;

/**
 * Created by rainbird on 12/28/15.
 */
public class FinanceRepository extends Panel {

//    @Inject
//    @Named("StockEntities")
//    private DataStore dataStore;

    //private ObjectProperty<String> listingName;

    private String defaultListingName = "List of S&P 500 companies.xml";

    public FinanceRepository() {
        super();
        initialize();
    }

    private void initialize() {

        //Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
        Injector injector = QoanInjectorService.getInstance().getInjector();
        injector.injectMembers(this);

        VerticalLayout layout = new VerticalLayout();

        // @TODO this part of the code needs to be replaced
        // now we load the list
//        Collection<String> listings = ((StockEntityDataStore) dataStore).getMarketListings();
//        ComboBox listingsBox = new ComboBox("Stock Listings");
//        listingName = new ObjectProperty<String>(defaultListingName, String.class);
//        listingsBox.setPropertyDataSource(listingName);
//        for (String name : listings) {
//            listingsBox.addItem(name);
//        }

//        layout.addComponent(listingsBox);
        setContent(layout);
    }

   /* public String getSelectedListingName() {
        return listingName.getValue();
    }*/

}
