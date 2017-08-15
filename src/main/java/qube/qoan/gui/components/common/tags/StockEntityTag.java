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
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import qube.qai.persistence.StockEntity;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.decorators.HistogramDecorator;
import qube.qoan.gui.components.common.decorators.StockQuotesDecorator;
import qube.qoan.gui.components.workspace.finance.StockEntityPanel;

/**
 * Created by rainbird on 12/28/15.
 */
public class StockEntityTag extends BaseTag {

    private StockEntity stockEntity;

    private Layout parentLayout;

    public StockEntityTag(Layout parentLayout, SearchResult searchResult) {
        super(parentLayout, searchResult);
        iconImage = new Image(STOCK_ENTITIES,
                new ClassResource("gui/images/stocks-index.png"));
        decorators.put("Stock Quote Statistics", new HistogramDecorator());
        decorators.put("Stock Quotes", new StockQuotesDecorator());
        decorators.put("Stock Entity", new StockEntityPanel());
    }

}
