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

package org.qoan.gui.components.workspace.procedure;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TreeGrid;
import org.qai.main.QaiConstants;
import org.qai.procedure.ProcedureLibraryInterface;
import org.qai.procedure.ProcedureTemplate;
import org.qai.services.DistributedSearchServiceInterface;
import org.qai.services.implementation.SearchResult;
import org.qoan.gui.components.common.SearchSettings;
import org.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Created by rainbird on 7/4/17.
 */
public class ProcedureSearchSink extends SearchSinkComponent {

    @Inject
    @Named("Procedures")
    private DistributedSearchServiceInterface searchService;

    @Inject
    private ProcedureLibraryInterface procedureLibrary;

    private SearchSettings searchSettings;

    private TreeData<SearchResult> treeData;

    protected TreeDataProvider<SearchResult> dataProvider;

    @Override
    protected void initializeSearchSettings() {
        this.name = "Procedures";
        this.context = "Procedures";
        searchSettings = new SearchSettings("Procedures", "Procedures", "This is for searching the procdures");
    }

    @Override
    public void doSearch(String searchString) {

        if (clearResults.getValue()) {
            onClearResults();
        }

        for (ProcedureTemplate template : procedureLibrary.getTemplateMap().values()) {
            // now with data in the datastore this makes sense to wait a bit longer.
            Collection<SearchResult> results = searchService.searchInputString(template.getProcedureName(), QaiConstants.PROCEDURES, 100);
            SearchResult procResult = new SearchResult(QaiConstants.PROCEDURE_TEMPLATES, template.getProcedureName(), "n/a", template.getProcedureDescription(), 1.0);
            if (!treeData.contains(procResult)) {
                treeData.addItem(null, procResult);
            }
            // if the results have returned nothing just go on tot eh next procedure.
            if (results == null || results.isEmpty()) {
                continue;
            } else {
                treeData.addItems(procResult, results);
            }
        }

        dataProvider.refreshAll();

        //Notification.show("You can drag'n'drop results from the grid to workspace to see their details");

    }

    @Override
    public Collection<SearchResult> getCurrentResult() {
        return dataProvider.getTreeData().getRootItems();
    }

    @Override
    public void delayedResults(Collection<SearchResult> results) {

        // for the moment being there is nothing to do here

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
    public void initialize() {
        super.initialize();
    }

}
