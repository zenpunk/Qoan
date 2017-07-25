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

package qube.qoan.gui.components.common.tags;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import qube.qai.persistence.StockEntity;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.decorators.HistogramDecorator;
import qube.qoan.gui.components.common.decorators.TimeSeriesDecorator;
import qube.qoan.gui.components.common.decorators.WikiDecorator;

/**
 * Created by rainbird on 12/28/15.
 */
public class StockEntityTag extends BaseTag {

    private StockEntity stockEntity;

    private Layout parentLayout;

    public StockEntityTag(SearchResult searchResult) {
        super(searchResult);
        iconImage = new Image("Yes, logo:",
                new ClassResource("gui/images/stocks-index.png"));
        decorators.put("Wiki Article", new WikiDecorator());
        decorators.put("Stock Quotes", new TimeSeriesDecorator());
        decorators.put("Stock Quote Statistics", new HistogramDecorator());
    }

    //    public StockEntityTag(StockEntity stockEntity, Layout parentLayout) {
//        super();
//
//        this.stockEntity = stockEntity;
//        this.parentLayout = parentLayout;
//
//        initialize();
//    }

    /**
     * initialize the whole thing here
     */
    @Override
    public void initialize() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        final String title = stockEntity.getUuid().toString();
        Label titleLabel = new Label(title);
        titleLabel.setStyleName("bold");
        layout.addComponent(titleLabel);

        Label sourceLabel = new Label("name: " + stockEntity.getSecurity());
        layout.addComponent(sourceLabel);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button open = new Button("open");
        open.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                // @TODO

            }
        });
        open.setStyleName("link");
        buttonsLayout.addComponent(open);

        // add a button to remove this tag from workspace
        Button remove = new Button("remove");
        remove.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
                // we are removing the toDecorate- which is the dnd-wrapper
                Component parent = getParent();
                parentLayout.removeComponent(parent);
            }
        });
        remove.setStyleName("link");
        buttonsLayout.addComponent(remove);

        layout.addComponent(buttonsLayout);

        setContent(layout);
    }
}
