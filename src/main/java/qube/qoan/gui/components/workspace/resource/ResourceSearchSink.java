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

package qube.qoan.gui.components.workspace.resource;

import com.hazelcast.core.HazelcastInstance;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.services.DistributedSearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.SearchSettings;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

import static qube.qai.main.QaiConstants.*;

public class ResourceSearchSink extends SearchSinkComponent {

    private Logger logger = LoggerFactory.getLogger("ResourceSearchSink");

    @Inject
    @Named("WikiResources_en")
    private DistributedSearchServiceInterface wikiResSearchService;

    @Inject
    @Named("PdfFileResources")
    private DistributedSearchServiceInterface pdfSearchService;

    @Inject
    @Named("MolecularResources")
    private DistributedSearchServiceInterface molecularSearchService;

    @Inject
    private HazelcastInstance hazelcastInstance;

    private SearchSettings wikiSettings;

    private SearchSettings pdfSettings;

    private SearchSettings moleSettings;

    private Collection<SearchResult> searchResults;

    protected AbstractDataProvider dataProvider;

    @Override
    protected void initializeSearchSettings() {

        this.name = "WikiResources, PdfFileResources & MolecularResources searches";
        this.context = "WikiResources_en, PdfFileResources, MolecularResources";

        wikiSettings = new SearchSettings("WikiResources_en", "WikiResources", "This is for searching the wiki-resources");
        pdfSettings = new SearchSettings("PdfFileResources", "PdfFileResources", "This is for searching the pdf-file resources");
        moleSettings = new SearchSettings("MolecularResources", "MolecularResources", "This is for searching the molecular-resources");

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
            wikiResSearchService.searchInputString(this, searchString, WIKIPEDIA_RESOURCES, wikiSettings.getNumResults());
        }

        if (pdfSettings.isInUse()) {
            pdfSearchService.searchInputString(this, searchString, PDF_FILE_RESOURCES, pdfSettings.getNumResults());
        }

        if (moleSettings.isInUse()) {
            molecularSearchService.searchInputString(this, searchString, MOLECULAR_RESOURCES, moleSettings.getNumResults());
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

        Notification.show("Resources search results received- drag items on desktop to open...");

    }

    @Override
    public SearchSettings getSettingsFor(String serviceName) {
        return wikiSettings;
    }

    @Override
    protected void onClearResults() {
        searchResults.clear();
        dataProvider.refreshAll();
    }
}
