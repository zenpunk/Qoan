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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.main.QaiConstants;
import qube.qai.services.SearchResultSink;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.QoanUI;

import java.util.Collection;

/**
 * Created by rainbird on 6/27/17.
 */
public class SearchSource extends Panel {

    private static Logger logger = LoggerFactory.getLogger("SearchSource");

    // do not inject the thing, because the name will be determined only later!
    // @Inject
    private SearchServiceInterface searchService;

    //@Inject
    private SearchResultSink resultSink;

    private String searchContext;

    private String name;

    private String description;

    private CheckBox inUseBox;

    private int numResults = 100;

    public SearchSource(String name, String searchContextName) {
        this.name = name;
        this.searchContext = searchContextName;
        initialize();
    }

    private void initialize() {

        searchService = ((QoanUI) UI.getCurrent()).getNamedService(searchContext);
        if (searchService == null) {
            String message = "No search-service with the name: '" + searchContext + "' has been found- this service will not be available!";
            logger.error(message);
            Notification.show(message);
        }


        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        Label nameLabel = new Label("Name: " + name);
        layout.addComponent(nameLabel);

        Label contextlabel = new Label("Context: '" + searchContext + "'");
        layout.addComponent(contextlabel);

        Label descLabel = new Label("Description: " + description);
        layout.addComponent(descLabel);

        inUseBox = new CheckBox("Search Active");
        inUseBox.setValue(true);
        layout.addComponent(inUseBox);
    }

    public void doSearch(String searchString) {

        // check if we are set active and just return if not
        if (!inUseBox.getValue()) {
            return;
        }

        Collection<SearchResult> results;
        if (QaiConstants.WIKIPEDIA.equals(searchContext)) {
            results = searchService.searchInputString(searchString, "title", numResults);
        } else if (QaiConstants.WIKTIONARY.equalsIgnoreCase(searchContext)) {
            results = searchService.searchInputString(searchString, "title", numResults);
        } else {
            results = searchService.searchInputString(searchString, searchContext, numResults);
        }

        if (resultSink != null) {
            resultSink.addResults(results);
        } else {
            Notification.show("Search dropped as there is no result sink to supply: '" + searchString + "'");
        }
    }

    public SearchResultSink getResultSink() {
        return resultSink;
    }

    public void setResultSink(SearchResultSink resultSink) {
        this.resultSink = resultSink;
    }

    public String getSearchContext() {
        return searchContext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
