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

import com.vaadin.ui.*;
import qube.qai.services.implementation.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rainbird on 6/27/17.
 */
public class SearchResultSink extends Panel {

    private Grid<SearchResult> resultGrid;

    private List<SearchResult> searchResults;

    private CheckBox clearResults;

    public SearchResultSink() {

        searchResults = new ArrayList<>();
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
//        resultGrid.addColumn(SearchResult::getUuid).setCaption("UUID");
        resultGrid.setWidth("100%");
        layout.addComponent(resultGrid);

        clearResults = new CheckBox("Clear results before adding new ones");
        layout.addComponent(clearResults);

        Button clearButton = new Button("Clear results");
        clearButton.addClickListener(clickEvent -> searchResults.clear());
        clearButton.setStyleName("link");
        layout.addComponent(clearButton);
    }

    public void addResults(Collection<SearchResult> results) {

        // if there are no results, don't bother add them
        if (results == null || results.isEmpty()) {
            return;
        }

        if (clearResults.getValue()) {
            resultGrid.setItems(new ArrayList<>());
            searchResults.clear();
        }

        searchResults.addAll(results);
        resultGrid.setItems(searchResults);
    }
}
