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

package qube.qoan.gui.components.workspace;

import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import qube.qoan.services.QoanInjectorService;

/**
 * Created by rainbird on 11/2/15.
 */
public class Workspace extends Panel {

    private TabSheet workspaceTabs;

    public Workspace() {
        super();
        initialize();
    }

    private void initialize() {

        // as always begin with initializing the layout
        //HorizontalLayout layout = new HorizontalLayout();

        workspaceTabs = new TabSheet();

        WorkspacePanel panel = new WorkspacePanel("Workspace 1");
        QoanInjectorService.getInstance().getInjector().injectMembers(panel);
        workspaceTabs.addTab(panel).setCaption("Workspace 1");

        //layout.addComponent(workspaceTabs);

        setContent(workspaceTabs);
    }

    /**
     * adds a new tab to the view... mainly for the button below
     */
    public void addNewTab() {
        int count = workspaceTabs.getComponentCount() + 1;
        String title = "Workspace " + count;
        WorkspacePanel panel = new WorkspacePanel(title);
        QoanInjectorService.getInstance().getInjector().injectMembers(panel);
        workspaceTabs.addTab(panel).setCaption(title);
    }

}
