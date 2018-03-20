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

package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import qube.qai.services.SearchResultSink;
import qube.qoan.gui.components.common.SearchMenu;
import qube.qoan.gui.components.common.search.SearchSinkComponent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureMenu extends SearchMenu {

    @Inject
    @Named("ProcedureResults")
    private SearchResultSink resultSink;

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
        initialize(PROCEDURES);
    }

    @Override
    protected SearchSinkComponent getResultSink() {
        return (SearchSinkComponent) resultSink;
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
