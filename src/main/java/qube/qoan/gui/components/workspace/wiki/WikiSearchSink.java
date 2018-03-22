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

package qube.qoan.gui.components.workspace.wiki;

import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import qube.qai.main.QaiConstants;
import qube.qai.services.DistributedSearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.SearchSettings;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rainbird on 7/16/17.
 */
public class WikiSearchSink extends SearchSinkComponent {

    @Inject
    @Named("Wikipedia_en")
    private DistributedSearchServiceInterface wikiService;

    @Inject
    @Named("Wiktionary_en")
    private DistributedSearchServiceInterface wiktionaryService;

    private SearchSettings wikiSettings;

    private SearchSettings wiktionarySettings;

    private Collection<SearchResult> searchResults;

    protected AbstractDataProvider dataProvider;

    public WikiSearchSink() {
        super();
    }

    @Override
    protected void initializeSearchSettings() {

        wikiSettings = new SearchSettings(QaiConstants.WIKIPEDIA, "Wikipedia", "This is for the searches in Wikipedia");

        wiktionarySettings = new SearchSettings(QaiConstants.WIKTIONARY, "Wiktionary", "This is for searches in Wiktionary");
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

        if (wikiSettings.isInUse()) {
            wikiService.searchInputString(this, searchString, "title", wikiSettings.getNumResults());
        }

        if (wiktionarySettings.isInUse()) {
            wiktionaryService.searchInputString(this, searchString, "title", wiktionarySettings.getNumResults());
        }

        dataProvider.refreshAll();

        if (!searchResults.isEmpty()) {
            Notification.show("You can drag'n'drop results from the grid to workspace to see their details");
        }
    }

    @Override
    public void delayedResults(Collection<SearchResult> results) {

        if (results == null || results.isEmpty()) {
            return;
        }

        for (SearchResult result : results) {
            if (!searchResults.contains(result)) {
                searchResults.add(result);
            }
        }

        dataProvider.refreshAll();
    }

    @Override
    protected void onClearResults() {
        searchResults.clear();
        dataProvider.refreshAll();
    }

    @Override
    public SearchSettings getSettingsFor(String serviceName) {
        if (QaiConstants.WIKIPEDIA.equalsIgnoreCase(serviceName)) {
            return wikiSettings;
        } else if (QaiConstants.WIKTIONARY.equalsIgnoreCase(serviceName)) {
            return wiktionarySettings;
        } else {
            return null;
        }
    }

}
