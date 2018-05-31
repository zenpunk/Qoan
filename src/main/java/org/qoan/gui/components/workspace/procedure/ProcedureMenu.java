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

package org.qoan.gui.components.workspace.procedure;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import org.qai.services.implementation.SearchResult;
import org.qoan.gui.components.common.SearchMenu;
import org.qoan.gui.components.common.search.SearchSinkComponent;
import org.qoan.services.QoanInjectorService;

import java.util.Collection;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureMenu extends SearchMenu {

    private SearchSinkComponent searchSink;

    private Image iconImage;

    private String captionTitle = "Procedure Templates & Instances";

    /**
     * this is the container for elements which will be
     * needed to design, run and gain access to results
     * of the procedures which have already run
     * For the moment being though, it will be limited only
     * to display the procedure and its results
     */
    public ProcedureMenu() {
        super();
    }

    @Override
    public void initialize() {
        iconImage = new Image("",
                new ClassResource("gui/images/proc-icon.png"));

        searchSink = new ProcedureSearchSink();
        QoanInjectorService.getInstance().injectMembers(searchSink);
        searchSink.initialize();

        this.searchToolTipText = "Currently no searches here- just click button to load sample set";

        initialize(searchSink, PROCEDURES);

        this.searchText.setEnabled(false);
        this.searchText.setValue("n/a");
        this.searchText.setDescription("Currently search is not implemented- click on search button to load sample data set.");
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
    public Image getMenuIcon() {
        return iconImage;
    }

    @Override
    public String getCaptionTitle() {
        return captionTitle;
    }

}
