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

package qube.qoan.gui.components.common;

import com.vaadin.ui.*;

/**
 * Created by rainbird on 12/3/15.
 */
public class WorkspacePanel extends Panel {

    private String title;

    private Layout parentLayout;

    /**
     * this class is a substitute to Vaadin's native
     * window, in order to remedy some of its drawbacks
     * InnerPanels can be opened, closed and dragged around
     * on the Workspace
     */
    public WorkspacePanel(String title, Layout parentLayout, Component content) {

        super();
        this.parentLayout = parentLayout;
        this.title = title;
        initialize(content);
    }

    private void initialize(Component content) {

        VerticalLayout innerLayout = new VerticalLayout();
        //innerLayout.setHeight("100%");
        //innerLayout.setWidth("100%");

        HorizontalLayout titleRow = new HorizontalLayout();
        //titleRow.setWidth("100%");
        //titleRow.setHeight("50px");

        Label titleLabel = new Label(title);
        titleLabel.setStyleName("bold");
        titleRow.addComponent(titleLabel);

        Button closeButton = new Button("X");
        closeButton.addClickListener(clickEvent -> onCLoseClicked());
        closeButton.setStyleName("link");
        titleRow.addComponent(closeButton);
        innerLayout.addComponent(titleRow);

        //VerticalLayout contentLayout = new VerticalLayout();
        //contentLayout.setWidth("100%");
        //contentLayout.setHeight("100%");
        //contentLayout.addComponent(content);
        //innerLayout.addComponent(contentLayout);
        content.setWidth("100%");
        content.setHeight("100%");
        innerLayout.addComponent(content);

        setContent(innerLayout);
    }

    public void onCLoseClicked() {
        parentLayout.removeComponent(this);
    }

}
