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

package org.qoan.gui.components.common;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class SearchSettings extends Panel {

    private String searchContext;

    private String name;

    private String description;

    private CheckBox inUseBox;

    private int numResults = 100;

    public SearchSettings(String searchContext, String name, String description) {
        super(name);
        this.searchContext = searchContext;
        this.name = name;
        this.description = description;

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

    public boolean isInUse() {
        return inUseBox.getValue();
    }

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }
}
