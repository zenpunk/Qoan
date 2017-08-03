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

package qube.qoan.gui.components.workspace.resource;

import qube.qai.services.SearchResultSink;
import qube.qoan.gui.components.common.QoanMenu;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rainbird on 1/16/16.
 */
public class ResourceMenu extends QoanMenu {

    @Inject
    @Named("ResourceResults")
    private SearchResultSink searchSink;

    /**
     * this is for adding and managing pdf-directories in the whole
     */
    public ResourceMenu() {
    }

    @Override
    public void initialize() {
        setCaption("Wiki-Resources, Pdf-Documents & Molecular-Viewer Resources");
        // WIKIPEDIA_RESOURCES, MOLECULAR_RESOURCES, PDF_FILE_RESOURCES
        initialize(WIKIPEDIA_RESOURCES, PDF_FILE_RESOURCES, MOLECULAR_RESOURCES);
    }

    @Override
    protected SearchSinkComponent getResultSink() {
        return (SearchSinkComponent) searchSink;
    }
}
