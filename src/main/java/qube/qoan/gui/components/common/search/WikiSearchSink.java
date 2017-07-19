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

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.dnd.DragSourceExtension;
import qube.qai.services.implementation.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rainbird on 7/16/17.
 */
public class WikiSearchSink extends SearchResultSinkComponent {

    protected Grid<SearchResult> resultGrid;

    private List<SearchResult> searchResults;

    protected ListDataProvider<SearchResult> searchResultProvider;

    public WikiSearchSink() {
        super();
    }

    @Override
    protected void initializeSearchResults() {
        searchResults = new ArrayList<>();
        SearchResult result = new SearchResult("Wikipedia_en", "S&P 500 Listing", "List of S&P 500 companies.xml", "S&P 500 Companies", 1.0);
        searchResults.add(result);
        searchResultProvider = DataProvider.ofCollection(searchResults);
    }

    @Override
    protected Grid createGrid(Collection<SearchResult> results) {

        Grid<SearchResult> grid = new Grid<>("Search Results");
        grid.addColumn(SearchResult::getContext).setCaption("Context");
        grid.addColumn(SearchResult::getTitle).setCaption("Title");
        grid.addColumn(SearchResult::getDescription).setCaption("Description");
        grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        grid.addColumn(SearchResult::getUuid).setCaption("UUID");

        if (results != null) {
            ListDataProvider<SearchResult> provider = DataProvider.ofCollection(results);
            grid.setDataProvider(provider);
        }

        DragSourceExtension<Grid<SearchResult>> dragSource = createDragSource(grid);

        grid.setWidth("100%");
        grid.setHeight("100%");
        return grid;
    }

    @Override
    public void addResults(Collection<SearchResult> results) {

        // if there are no results, don't bother add them
        if (results == null || results.isEmpty()) {
            return;
        }
        searchResultProvider = DataProvider.ofCollection(searchResults);
        searchResultProvider.refreshAll();
//
//        // @TODO i hope i can change this soon back to what it should be
//        Window window = new Window("Search results: " + results.size() + " of them...");
//        Grid<SearchResult> grid = createGrid(results);
//        window.setContent(grid);
//        window.setWidth("800px");
//        window.setHeight("600px");
//
//        UI.getCurrent().addWindow(window);

//        if (clearResults.getValue()) {
//            searchResults.clear();
//        }
//        // updating the grid has a problem...
//        Notification.show("Adding " + results.size() + " rows for display");
//        searchResults.addAll(results);
//        resultGrid.getDataProvider().refreshAll();
//        //initialize();
    }
}
