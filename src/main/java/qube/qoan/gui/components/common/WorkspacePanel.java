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
import org.apache.commons.lang3.StringUtils;

/**
 * Created by rainbird on 12/3/15.
 */
public class WorkspacePanel extends Panel {

    /**
     * this class is a substitute to Vaadin's native
     * window, in order to remedy some of its drawbacks
     * InnerPanels can be opened, closed and dragged around
     * on the Workspace
     */
    public WorkspacePanel(String title, Component content) {

        super();

        initialize(title, content);
    }

    private void initialize(String title, Component content) {

        // Begin with layout
        //VerticalLayout layout = new VerticalLayout();

        AbsoluteLayout topLayout = new AbsoluteLayout();
        topLayout.setWidth("600px");
        topLayout.setHeight("400px");
        // add the title only if there is actually something there
        if (StringUtils.isNotBlank(title)) {
            Label titleLabel = new Label(title);
            titleLabel.setStyleName("bold");
            topLayout.addComponent(titleLabel, "top:10px; left:10px;");
        }

        Button closeButton = new Button("X");
        closeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setVisible(false);
            }
        });
        closeButton.setStyleName("link");
        topLayout.addComponent(closeButton, "top:0px; right:10px;");
        topLayout.setStyleName("hover");
        //layout.addComponent(topLayout);

        // now add the given component
        Panel contentPanel = new Panel(content);
        contentPanel.setWidth("380px");
        contentPanel.setHeight("370px");
        //layout.addComponent(content);
        topLayout.addComponent(content, "top:30; right:0px; bottom:1px; left:0px;");
        //layout.setMargin(true);
        //setContent(layout);
        setContent(topLayout);
    }

}
