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
import qube.qai.services.SearchServiceInterface;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.common.search.SearchSinkComponent;
import qube.qoan.gui.components.workspace.wiki.SearchSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rainbird on 7/1/17.
 */
public abstract class SearchMenu extends Panel implements QaiConstants {

    protected boolean firstClick = true;

    protected boolean initialized = false;

    protected VerticalLayout layout;

    protected List<SearchSource> searchSources;

    protected Accordion searchSettings;

    public SearchMenu() {
        searchSources = new ArrayList<>();
    }

    public abstract void initialize();

    protected abstract SearchSinkComponent getResultSink();

    public abstract Image getMenuIcon();

    public abstract String getCaptionTitle();

    public boolean isInitialized() {
        return initialized;
    }

    protected void initialize(String... serviceNames) {

        if (initialized) {
            return;
        }

        SearchSinkComponent resultSink = getResultSink();
        resultSink.initialize();

        layout = new VerticalLayout();
        // make the wiki-settings initially invisible for the sake of clarity
        searchSettings = new Accordion();
        searchSettings.setCaption("Search settings");
        searchSettings.setVisible(false);

        // put the text-field and the wiki buton on the same row
        HorizontalLayout searchRow = new HorizontalLayout();

        TextField searchText = new TextField("Search");
        searchRow.addComponent(searchText);

        Button doSearch = new Button("Do Search");
        doSearch.setStyleName("link");
        doSearch.addClickListener(clickEvent -> this.doSearch(searchText.getValue()));
        searchRow.addComponent(doSearch);
        layout.addComponent(searchRow);

        HorizontalLayout toggleLine = new HorizontalLayout();
        Button toggleSettingsButton = new Button("Toggle wiki-setting visibility");
        toggleSettingsButton.setStyleName("link");
        toggleSettingsButton.addClickListener(clickEvent -> searchSettings.setVisible(!searchSettings.isVisible()));
        toggleLine.addComponent(toggleSettingsButton);

        Button toggleResultsButton = new Button("Toggle wiki results visibility");
        toggleResultsButton.setStyleName("link");
        toggleResultsButton.addClickListener(clickEvent -> resultSink.setVisible(!resultSink.isVisible()));
        toggleLine.addComponent(toggleResultsButton);

        layout.addComponent(toggleLine);

        //Set<String> serviceNames = ((QoanUI) UI.getCurrent()).getSearchServiceNames();
        for (String name : serviceNames) {
            SearchServiceInterface service = ((QoanUI) UI.getCurrent()).getNamedService(name);
            SearchSource source = new SearchSource(name, name);
            source.setResultSink(resultSink);
            searchSettings.addTab(source, source.getName());
            searchSources.add(source);
        }
        searchSettings.setWidth("100%");
        layout.addComponent(searchSettings);

        // and finally below all else put the result sink
        layout.addComponent(resultSink);
        setContent(layout);

        initialized = true;
    }

    public void doSearch(String searchString) {
        for (SearchSource source : searchSources) {
            source.doSearch(searchString);
        }
        if (firstClick) {
            Notification.show("You can drag'n'drop results from the grid to workspace to see their details");
            firstClick = false;
        }
    }
}
