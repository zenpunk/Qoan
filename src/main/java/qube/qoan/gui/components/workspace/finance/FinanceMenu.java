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

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 11/18/15.
 */
public class FinanceMenu extends Panel {

    private FinanceListing financeListing;
    private FinanceRepository financeRepository;

    public FinanceMenu() {

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();
        //layout.setWidth("300px");

        financeRepository = new FinanceRepository();
        layout.addComponent(financeRepository);

        Button showSelectedListingButton = new Button("Show selected listing");
        showSelectedListingButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                String listingName = financeRepository.getSelectedListingName();
                financeListing.displayListing(listingName);
            }
        });
        showSelectedListingButton.setStyleName("link");
        layout.addComponent(showSelectedListingButton);

        financeListing = new FinanceListing();
        layout.addComponent(financeListing);

        setContent(layout);
    }
}
