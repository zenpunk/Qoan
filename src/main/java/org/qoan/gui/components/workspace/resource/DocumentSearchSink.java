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

package org.qoan.gui.components.workspace.resource;

import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import org.qai.services.implementation.SearchResult;
import org.qoan.gui.components.common.SearchSettings;
import org.qoan.gui.components.common.search.SearchSinkComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentSearchSink extends SearchSinkComponent {

    private SearchSettings searchSettings;

    private List<SearchResult> searchResults;

    protected AbstractDataProvider dataProvider;

    public DocumentSearchSink() {

    }

    @Override
    protected void initializeSearchSettings() {

        this.name = "Documents";
        this.context = "Documents";

        searchSettings = new SearchSettings("Documents", "Documents", "This is for searching for documents");
    }

    @Override
    protected Grid createGrid() {

        Grid<SearchResult> grid = new Grid<>("Search Results");
        grid.addColumn(SearchResult::getTitle).setCaption("Title");
        grid.addColumn(SearchResult::getDescription).setCaption("Description");
        grid.addColumn(SearchResult::getContext).setCaption("Context");
        grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        grid.addColumn(SearchResult::getUuid).setCaption("UUID");
        grid.setWidth("100%");
        grid.setHeight("100%");

        searchResults = new ArrayList<>();
        dataProvider = DataProvider.ofCollection(searchResults);
        grid.setDataProvider(dataProvider);

        return grid;
    }

    @Override
    public void doSearch(String searchString) {

        if (clearResults.getValue()) {
            onClearResults();
        }

        //results = searchService.searchInputString(searchString, "Stock_Groups", searchSettings.getNumResults());

        if (!searchResults.isEmpty()) {
            Notification.show("You can drag'n'drop results from the grid to workspace to see their details");
        }
    }

    @Override
    public Collection<SearchResult> getCurrentResult() {
        return searchResults;
    }

    @Override
    public void delayedResults(Collection<SearchResult> results) {

    }


    @Override
    public SearchSettings getSettingsFor(String serviceName) {
        return searchSettings;
    }

    @Override
    protected void onClearResults() {
        searchResults.clear();
        dataProvider.refreshAll();
    }

}
