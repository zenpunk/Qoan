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

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TreeGrid;
import qube.qai.main.QaiConstants;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.StockEntity;
import qube.qai.persistence.StockGroup;
import qube.qai.services.DistributedSearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.SearchSettings;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Created by rainbird on 7/4/17.
 */
public class FinanceSearchSink extends SearchSinkComponent implements QaiConstants {

    @Inject
    @Named("Stock_Groups")
    private DistributedSearchServiceInterface searchService;

    @Inject
    private QaiDataProvider<StockGroup> qaiDataProvider;

    private SearchSettings searchSettings;

    private ProgressBar progress;

    private TreeData<SearchResult> treeData;

    protected TreeDataProvider<SearchResult> dataProvider;

    public FinanceSearchSink() {
        super();
    }

    @Override
    protected void initializeSearchSettings() {

        this.name = "Finance-Entities";
        this.context = "Stock_Groups, Stock_Entities";

        searchSettings = new SearchSettings("Stock_Groups", "Finance-Entities", "This is for listing the finance-entities");
    }

    @Override
    protected Grid createGrid() {

        TreeGrid<SearchResult> grid = new TreeGrid<>();
        grid.addColumn(SearchResult::getTitle).setCaption("Title");
        grid.addColumn(SearchResult::getDescription).setCaption("Description");
        grid.addColumn(SearchResult::getContext).setCaption("Context");
        grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        grid.addColumn(SearchResult::getUuid).setCaption("UUID");
        grid.setWidth("100%");
        grid.setHeight("100%");

        treeData = new TreeData<>();
        dataProvider = new TreeDataProvider<>(treeData);
        grid.setDataProvider(dataProvider);

        return grid;
    }

    @Override
    public void doSearch(String searchString) {

        if (clearResults.getValue()) {
            onClearResults();
        }

        if (searchSettings.isInUse()) {
            searchService.searchInputString(this, "*", QaiConstants.STOCK_GROUPS, searchSettings.getNumResults());
        }

        progress = new ProgressBar();

    }

    @Override
    public Collection<SearchResult> getCurrentResult() {
        return dataProvider.getTreeData().getRootItems();
    }

    @Override
    public void delayedResults(Collection<SearchResult> results) {

        if (results == null || results.isEmpty()) {
            return;
        }

        try {
            for (SearchResult result : results) {

                if (!treeData.contains(result)) {
                    treeData.addItem(null, result);

                    StockGroup group = qaiDataProvider.brokerSearchResult(result);
                    Collection<StockEntity> entities = group.getEntities();
                    for (StockEntity entity : entities) {
                        SearchResult sResult = new SearchResult(QaiConstants.STOCK_ENTITIES, entity.getName(), entity.getUuid(), entity.getTickerSymbol(), 1.0);
                        treeData.addItem(result, sResult);
                    }
                }
            }
        } catch (Exception e) {
            // ignore this exception
        } finally {

            dataProvider.refreshAll();

//            Notification.show("Finance entities received- drag items on desktop to open...");
        }
    }

    @Override
    public SearchSettings getSettingsFor(String serviceName) {
        return searchSettings;
    }

    @Override
    protected void onClearResults() {
        treeData.clear();
        dataProvider.refreshAll();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

}
