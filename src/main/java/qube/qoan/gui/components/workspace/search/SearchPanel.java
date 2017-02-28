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

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.*;
import qube.qoan.gui.interfaces.SearchAgent;

/**
 * Created by rainbird on 11/12/15.
 */
public class SearchPanel extends Panel {

    private SearchAgent searchAgent;

    public SearchPanel(SearchAgent searchAgent) {
        super();

        this.searchAgent = searchAgent;

        initialize();
    }

    private void initialize() {
        VerticalLayout layout = new VerticalLayout();

        // now we add a select-box for wiki-source
        final ObjectProperty wikiSource = new ObjectProperty("Wikipedia", String.class);
        ComboBox wikiSourceBox = new ComboBox("Wiki Source");
        wikiSourceBox.addItem("Wikipedia");
        wikiSourceBox.addItem("Wiktionary");
        wikiSourceBox.addItem("Wikipedia & Wiktionary");
        wikiSourceBox.setPropertyDataSource(wikiSource);
        layout.addComponent(wikiSourceBox);

        // use a select box for the part where the search should take place
        final ObjectProperty searchIn = new ObjectProperty("title", String.class);
        ComboBox searchInBox = new ComboBox("Search In");
        searchInBox.addItem("title and content");
        searchInBox.addItem("title");
        searchInBox.addItem("content");
        searchInBox.setPropertyDataSource(searchIn);
        layout.addComponent(searchInBox);

        // text field for maximum results
//        ObjectProperty searchLimit = new ObjectProperty("Max Hits", Integer.class);
//        TextField searchLimitField = new TextField("Max Hits", searchLimit);
//        parentLayout.addComponent(searchLimitField);

        // text field for the actual search term to be used
        final ObjectProperty searchTerm = new ObjectProperty("", String.class);
        TextField searchField = new TextField("Search Term", searchTerm);
        layout.addComponent(searchField);

        // and finally the button to activate the search
        Button searchButton = new Button("Search");
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String source = (String) wikiSource.getValue();
                String part = (String) searchIn.getValue();
                String search = (String) searchTerm.getValue();
                searchAgent.searchFor(source, search, part, 100);
            }
        });
        searchButton.setStyleName("link");
        layout.addComponent(searchButton);

        setContent(layout);
    }
}
