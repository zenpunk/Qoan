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

package qube.qoan.gui.components.common.search;

import com.google.inject.Injector;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TreeGrid;
import qube.qai.main.QaiConstants;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.StockEntity;
import qube.qai.persistence.StockGroup;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.QoanUI;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rainbird on 7/4/17.
 */
public class FinanceSearchResultSink extends SearchResultSinkComponent {

    @Inject
    @Named("Stock_Groups")
    private SearchServiceInterface searchService;

    @Inject
    private QaiDataProvider<StockGroup> dataProvider;

    @Override
    protected void initializeSearchResults() {

        // self-inoculation
        Injector injector = ((QoanUI) QoanUI.getCurrent()).getInjector();
        injector.injectMembers(this);

        Collection<SearchResult> results = searchService.searchInputString("*", QaiConstants.STOCK_GROUPS, 100);

        if (results == null) {
            return;
        }

        for (SearchResult result : results) {
            StockGroup stockGroup = dataProvider.brokerSearchResult(result);
            Collection<StockEntity> entities = stockGroup.getEntities();
            Collection<SearchResult> entitiesAsResult = new ArrayList<>();
            for (StockEntity entity : entities) {
                SearchResult r = new SearchResult(QaiConstants.STOCK_ENTITIES, entity.getName(), entity.getUuid(), entity.getTickerSymbol(), 1.0);
                entitiesAsResult.add(r);
            }

            TreeDataProvider<SearchResult> gridDataProvider = (TreeDataProvider<SearchResult>) ((TreeGrid<SearchResult>) resultGrid).getDataProvider();
            TreeData<SearchResult> data = gridDataProvider.getTreeData();
            data.addItem(null, result);
            data.addItems(result, entitiesAsResult);
            gridDataProvider.refreshAll();
        }
    }

    @Override
    protected Grid createGrid() {

        TreeGrid<SearchResult> grid = new TreeGrid<>();
        grid.addColumn(SearchResult::getContext).setCaption("Context");
        grid.addColumn(SearchResult::getTitle).setCaption("Title");
        grid.addColumn(SearchResult::getDescription).setCaption("Description");
        grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        grid.addColumn(SearchResult::getUuid).setCaption("UUID");
        grid.setWidth("100%");
        grid.setHeight("100%");
        return grid;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void addResults(Collection<SearchResult> results) {
        // do nothing- the initialization at start should be sufficient
    }
}
