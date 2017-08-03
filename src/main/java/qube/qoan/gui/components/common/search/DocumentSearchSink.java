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

import com.vaadin.ui.Grid;
import qube.qai.services.implementation.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentSearchSink extends SearchSinkComponent {

    private List<SearchResult> searchResults;

    public DocumentSearchSink() {
        searchResults = new ArrayList<>();
    }

    @Override
    protected void initializeSearchResults() {

    }

    @Override
    protected Grid createGrid() {

        Grid<SearchResult> grid = new Grid<>("Search Results");
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
    public void addResults(Collection<SearchResult> results) {

        // if there are no results, don't bother add them
        if (results == null || results.isEmpty()) {
            return;
        }

        if (clearResults.getValue()) {
            searchResults.clear();
        }

        searchResults.addAll(results);
        resultGrid.setItems(searchResults);
        resultGrid.getDataProvider().refreshAll();
    }
}
