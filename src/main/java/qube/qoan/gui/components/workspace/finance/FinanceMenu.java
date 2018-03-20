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

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import qube.qai.services.SearchResultSink;
import qube.qoan.gui.components.common.SearchMenu;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rainbird on 11/18/15.
 */
public class FinanceMenu extends SearchMenu {

    @Inject
    @Named("FinanceResults")
    private SearchResultSink resultSink;

    private Image iconImage;

    private String captionTitle = "Stock Groups & Entities";

    public FinanceMenu() {

    }

    @Override
    public void initialize() {
        iconImage = new Image("Selection",
                new ClassResource("gui/images/stocks-index-icon.png"));
        initialize(STOCK_GROUPS);
    }

    @Override
    protected SearchSinkComponent getResultSink() {
        return (SearchSinkComponent) resultSink;
    }

    @Override
    public Image getMenuIcon() {
        return iconImage;
    }

    @Override
    public String getCaptionTitle() {
        return captionTitle;
    }
}
