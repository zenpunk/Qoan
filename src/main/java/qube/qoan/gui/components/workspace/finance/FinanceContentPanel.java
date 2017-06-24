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

import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.v7.ui.VerticalLayout;
import qube.qai.persistence.StockEntity;

/**
 * Created by rainbird on 6/6/16.
 */
public class FinanceContentPanel extends Panel {

    private StockEntity stockEntity;

    public FinanceContentPanel(StockEntity stockEntity) {
        this.stockEntity = stockEntity;

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();

        final TabSheet tabbedContent = new TabSheet();

    }
}
