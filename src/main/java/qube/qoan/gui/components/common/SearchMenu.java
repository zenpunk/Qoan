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

package qube.qoan.gui.components.common;

import com.vaadin.ui.*;
import qube.qai.main.QaiConstants;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import java.util.Collection;

/**
 * Created by zenpunk on 7/1/17.
 */
public abstract class SearchMenu extends Panel implements QaiConstants {

    protected boolean initialized = false;

    protected VerticalLayout layout;

    protected Accordion searchSettings;

    protected Button doSearch;

    protected TextField searchText;

    protected String searchToolTipText = "Click button to start search";

    public SearchMenu() {
        super();
    }

    public abstract void doSearch(String searchString);

    public abstract Collection<SearchResult> getCurrentResult();

    public abstract void initialize();

    public abstract Image getMenuIcon();

    public abstract String getCaptionTitle();

    public boolean isInitialized() {
        return initialized;
    }

    protected void initialize(SearchSinkComponent searchSink, String... serviceNames) {

        if (initialized) {
            return;
        }

        layout = new VerticalLayout();
        // make the wiki-settings initially invisible for the sake of clarity
        searchSettings = new Accordion();
        searchSettings.setCaption("Search settings");
        searchSettings.setVisible(false);

        // put the text-field and the wiki buton on the same row
        HorizontalLayout searchRow = new HorizontalLayout();

        searchText = new TextField("Search");
        searchRow.addComponent(searchText);

        doSearch = new Button("Do Search");
        doSearch.setStyleName("link");
        doSearch.setDescription(searchToolTipText);
        doSearch.addClickListener(clickEvent -> searchSink.doSearch(searchText.getValue()));
        searchRow.addComponent(doSearch);
        layout.addComponent(searchRow);

        HorizontalLayout toggleLine = new HorizontalLayout();
        Button toggleSettingsButton = new Button("Toggle search settings visibility");
        toggleSettingsButton.setStyleName("link");
        toggleSettingsButton.addClickListener(clickEvent -> searchSettings.setVisible(!searchSettings.isVisible()));
        toggleLine.addComponent(toggleSettingsButton);

        Button toggleResultsButton = new Button("Toggle search results visibility");
        toggleResultsButton.setStyleName("link");
        toggleResultsButton.addClickListener(clickEvent -> searchSink.setVisible(!searchSink.isVisible()));
        toggleLine.addComponent(toggleResultsButton);

        layout.addComponent(toggleLine);

        //Set<String> serviceNames = ((QoanUI) UI.getCurrent()).getSearchServiceNames();
        for (String name : serviceNames) {
            searchSettings.addTab(searchSink.getSettingsFor(name), name);
        }
        searchSettings.setWidth("100%");
        layout.addComponent(searchSettings);

        // and finally below all else put the result sink
        layout.addComponent(searchSink);
        setContent(layout);

        initialized = true;
    }

}
