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

import com.google.inject.Injector;
import com.vaadin.ui.*;
import qube.qai.main.QaiConstants;
import qube.qai.services.SearchServiceInterface;
import qube.qoan.QoanUI;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by rainbird on 10/31/15.
 */
public class SearchMenu extends Panel implements QaiConstants {

    private Injector injector;

    private VerticalLayout layout;

    @Inject
    private SearchResultSink resultSink;

    private List<SearchSource> searchSources;

    private Accordion searchSettings;

    public SearchMenu() {
        super();

        searchSources = new ArrayList<>();
        // start with self-inoculation
        injector = ((QoanUI) UI.getCurrent()).getInjector();
        // this can only happen in case of tests, obviously
        if (injector != null) {
            injector.injectMembers(this);
        }

        initialize();
    }

    private void initialize() {

        layout = new VerticalLayout();

        searchSettings = new Accordion();
        searchSettings.setCaption("Search settings");

        TextField searchText = new TextField("Search");
        layout.addComponent(searchText);

        Button doSearch = new Button("Do Search");
        doSearch.addClickListener(clickEvent -> this.doSearch(searchText.getValue()));
        layout.addComponent(doSearch);

        Button toggleButton = new Button("Toggle search-setting visibility");
        toggleButton.addClickListener(clickEvent -> searchSettings.setVisible(!searchSettings.isVisible()));
        layout.addComponent(toggleButton);

        Set<String> serviceNames = ((QoanUI) UI.getCurrent()).getSearchServiceNames();
        for (String name : serviceNames) {
            SearchServiceInterface service = ((QoanUI) UI.getCurrent()).getNamedService(name);
            SearchSource source = new SearchSource(name, name);
            searchSettings.addTab(source, source.getName());
            searchSources.add(source);
        }
        layout.addComponent(searchSettings);

        // and finally below all else put the result sink
        resultSink.initialize();
        layout.addComponent(resultSink);

        setContent(layout);
    }


    private void doSearch(String searchString) {
        for (SearchSource source : searchSources) {
            source.doSearch(searchString);
        }
    }
}
