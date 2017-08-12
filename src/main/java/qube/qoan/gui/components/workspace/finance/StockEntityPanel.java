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

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.StockEntity;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.decorators.BaseDecorator;

import javax.inject.Inject;

/**
 * Created by rainbird on 6/6/16.
 */
public class StockEntityPanel extends BaseDecorator {

    @Inject
    private QaiDataProvider<StockEntity> dataProvider;

    private Image iconImage;

    private String name = "Financial Entity";

    private StockEntity stockEntity;

    public StockEntityPanel() {
        iconImage = new Image(name,
                new ClassResource("gui/images/stocks-index-icon.png"));
    }

    public StockEntityPanel(StockEntity entity) {
        this();
        this.stockEntity = entity;
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        if (stockEntity == null) {
            stockEntity = dataProvider.brokerSearchResult(toDecorate);
        }

        VerticalLayout layout = new VerticalLayout();

        Label name = new Label("Name: " + stockEntity.getName());
        layout.addComponent(name);

        Label ticker = new Label("Ticker Symbol: " + stockEntity.getTickerSymbol());
        layout.addComponent(ticker);

        Label security = new Label("Security: " + stockEntity.getSecurity());
        layout.addComponent(security);

        Label gicsSector = new Label("GICS Sector: " + stockEntity.getGicsSector());
        layout.addComponent(gicsSector);

        Label price = new Label("Market Price: " + stockEntity.getMarketPrice());
        layout.addComponent(price);

        Label firstAdded = new Label("Date first added: " + stockEntity.getDateFirstAdded());
        layout.addComponent(firstAdded);

        Label yield = new Label("Yield: " + stockEntity.getYield());
        layout.addComponent(yield);

        Label earnings = new Label("Earnings: " + stockEntity.getEarnings());
        layout.addComponent(earnings);

        Label share = new Label("Share: " + stockEntity.getShare());
        layout.addComponent(share);

        Label sales = new Label("Sales: " + stockEntity.getSales());
        layout.addComponent(sales);

        setContent(layout);
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public String getName() {
        return name;
    }

}
