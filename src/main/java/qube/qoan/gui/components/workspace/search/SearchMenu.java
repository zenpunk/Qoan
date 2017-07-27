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

import qube.qai.services.SearchResultSink;
import qube.qoan.gui.components.common.QoanMenu;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rainbird on 10/31/15.
 */
public class SearchMenu extends QoanMenu {

    @Inject
    @Named("WikiResults")
    private SearchResultSink resultSink;

    public SearchMenu() {
        super();
    }

    @Override
    public void initialize() {
        setCaption("Wikipedia & Wiktionary Entries");
        initialize(WIKIPEDIA, WIKTIONARY);
    }

    @Override
    protected SearchSinkComponent getResultSink() {
        return (SearchSinkComponent) resultSink;
    }
}
