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

package qube.qoan.gui.components.workspace.search;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import qube.qai.services.SearchResultSink;
import qube.qai.services.implementation.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rainbird on 6/27/17.
 */
public class SearchResultSinkComponent extends Panel implements SearchResultSink {

    private Grid<SearchResult> resultGrid;

    private List<SearchResult> searchResults;

    ListDataProvider<SearchResult> searchResultProvider;

    private CheckBox clearResults;

    public SearchResultSinkComponent() {

        searchResults = new ArrayList<>();
        SearchResult result = new SearchResult("Wikipedia_en", "S&P 500 Listing", "List of S&P 500 companies.xml", "S&P 500 Companies", 1.0);
        searchResults.add(result);
        searchResultProvider = DataProvider.ofCollection(searchResults);
        initialize();

    }

    /**
     * initialize the thing only when you actually will need it
     */
    public void initialize() {

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        resultGrid = new Grid<>("Search Results");
        resultGrid.addColumn(SearchResult::getContext).setCaption("Context");
        resultGrid.addColumn(SearchResult::getTitle).setCaption("Title");
        resultGrid.addColumn(SearchResult::getDescription).setCaption("Description");
        resultGrid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        resultGrid.addColumn(SearchResult::getUuid).setCaption("UUID");
        resultGrid.setWidth("100%");
        resultGrid.setDataProvider(searchResultProvider);

        layout.addComponent(resultGrid);

        clearResults = new CheckBox("Clear results before adding new ones");
        layout.addComponent(clearResults);

        Button clearButton = new Button("Clear results");
        clearButton.addClickListener(clickEvent -> searchResults.clear());
        clearButton.setStyleName("link");
        layout.addComponent(clearButton);
    }

    private Grid createGrid(Collection<SearchResult> results) {

        ListDataProvider<SearchResult> provider = DataProvider.ofCollection(results);

        Grid<SearchResult> grid = new Grid<>("Search Results");
        grid.addColumn(SearchResult::getContext).setCaption("Context");
        grid.addColumn(SearchResult::getTitle).setCaption("Title");
        grid.addColumn(SearchResult::getDescription).setCaption("Description");
        grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        grid.addColumn(SearchResult::getUuid).setCaption("UUID");
        grid.setDataProvider(provider);
        grid.setWidth("100%");
        grid.setHeight("100%");
        return grid;
    }

    public void addResults(Collection<SearchResult> results) {

        // if there are no results, don't bother add them
        if (results == null || results.isEmpty()) {
            return;
        }

        // @TODO i hope i can change this soon back to what it should be
        Window window = new Window("Search results: " + results.size() + " of them...");
        Grid<SearchResult> grid = createGrid(results);
        window.setContent(grid);
        window.setWidth("800px");
        window.setHeight("600px");

        UI.getCurrent().addWindow(window);

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
