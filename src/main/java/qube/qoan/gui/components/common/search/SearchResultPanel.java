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

package qube.qoan.gui.components.common.search;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qai.services.implementation.SearchResult;


/**
 * Created by rainbird on 7/7/17.
 */
public class SearchResultPanel extends Panel {

    public SearchResultPanel(SearchResult searchResult) {

        VerticalLayout layout = new VerticalLayout();

        Label context = new Label("Context: '" + searchResult.getContext() + "'");
        layout.addComponent(context);

        Label title = new Label("Title: '" + searchResult.getTitle() + "'");
        layout.addComponent(title);

        Label uuid = new Label("UUID: '" + searchResult.getUuid() + "'");
        layout.addComponent(uuid);

        setContent(layout);
    }
}
