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

package org.qoan.gui.components.workspace.wiki;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import org.qai.services.implementation.SearchResult;
import org.qoan.gui.components.common.SearchMenu;
import org.qoan.gui.components.common.search.SearchSinkComponent;
import org.qoan.services.QoanInjectorService;

import java.util.Collection;

/**
 * Created by rainbird on 10/31/15.
 */
public class WikiMenu extends SearchMenu {

    private SearchSinkComponent searchSink;

    private Image iconImage;

    private String captionTitle = "Wikipedia & Wiktionary Entries";

    public WikiMenu() {
        super();
    }

    @Override
    public void doSearch(String searchString) {
        searchSink.doSearch(searchString);
    }

    @Override
    public Collection<SearchResult> getCurrentResult() {
        return searchSink.getCurrentResult();
    }

    @Override
    public void initialize() {
        iconImage = new Image("",
                new ClassResource("gui/images/wiki-icon.png"));

        searchSink = new WikiSearchSink();
        QoanInjectorService.getInstance().injectMembers(searchSink);
        searchSink.initialize();

        initialize(searchSink, WIKIPEDIA_EN, WIKTIONARY_EN);

    }

    @Override
    public Image getMenuIcon() {
        return iconImage;
    }

    @Override
    public String getCaptionTitle() {
        return captionTitle;
    }


}
