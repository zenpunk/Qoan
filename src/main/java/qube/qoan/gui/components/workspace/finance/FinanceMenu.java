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
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.SearchMenu;
import qube.qoan.gui.components.common.search.SearchSinkComponent;
import qube.qoan.services.QoanInjectorService;

import java.util.Collection;

/**
 * Created by rainbird on 11/18/15.
 */
public class FinanceMenu extends SearchMenu {

    private SearchSinkComponent searchSink;

    private Image iconImage;

    private String captionTitle = "Stock Groups & Entities";

    public FinanceMenu() {

    }

    @Override
    public void initialize() {
        iconImage = new Image("Stock Groups & Entities",
                new ClassResource("gui/images/stocks-index-icon.png"));

        searchSink = new FinanceSearchSink();
        searchSink.initialize();
        QoanInjectorService.getInstance().injectMembers(searchSink);

        this.searchToolTipText = "Currently no searches here- just click button to load sample set";

        initialize(searchSink, STOCK_GROUPS);

        this.searchText.setEnabled(false);
        this.searchText.setValue("n/a");
        this.searchText.setDescription("Currently search is not implemented- click on search button to load sample data set.");
    }

    @Override
    public void doSearch(String searchString) {
        searchSink.doSearch(searchString);
    }

    @Override
    public Collection<SearchResult> getCurrentResult() {
        return searchSink.getCurrentResult();
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
