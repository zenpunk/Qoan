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

package org.qoan.gui.components.workspace;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import org.qai.persistence.QaiDataProvider;
import org.qai.persistence.WikiArticle;
import org.slf4j.Logger;

import javax.inject.Inject;

/**
 * Created by rainbird on 11/14/15.
 */
public class WorkspacePanel extends Panel {

    @Inject
    private static Logger logger;

    @Inject
    private QaiDataProvider<WikiArticle> wikiProvider;

    private String title;

    public WorkspacePanel(String title) {
        this.title = title;
        initialize();
    }

    private void initialize() {

        AbsoluteLayout layout = new AbsoluteLayout();
        WorkspaceDropExtension dropExtension = new WorkspaceDropExtension(layout);
        dropExtension.addListener();

        Label titleLabel = new Label(title);
        layout.addComponent(titleLabel, "left: 50px; top: 50px;");
        layout.setWidth("1000px");
        layout.setHeight("800px");
        setContent(layout);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}