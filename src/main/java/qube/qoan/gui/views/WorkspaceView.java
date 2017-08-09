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

package qube.qoan.gui.views;

import com.google.inject.Injector;
import com.vaadin.ui.*;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.workspace.Workspace;
import qube.qoan.gui.components.workspace.finance.FinanceMenu;
import qube.qoan.gui.components.workspace.procedure.ProcedureMenu;
import qube.qoan.gui.components.workspace.resource.ResourceMenu;
import qube.qoan.gui.components.workspace.search.WikiSearchMenu;


/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends QoanView {

    private boolean initialized = false;

    private boolean debug = true;

    public static String NAME = "workspace";

    private WikiSearchMenu wikiSearchMenu;

    private FinanceMenu financeMenu;

    private ProcedureMenu procedureMenu;

    private ResourceMenu resourceMenu;

    private Workspace workspace;

    private HorizontalSplitPanel splitPanel;

    public WorkspaceView() {
        this.viewTitle = "Qoan Workspace";
    }

    /**
     * adds all of the components to the view
     */
    protected void initialize() {

        // see if we have already done this... whatever for. is there really need for this?
        if (initialized) {
            return;
        }

        // so that we can inject the members of the class already now for their correct initialization
        Injector injector = ((QoanUI) QoanUI.getCurrent()).getInjector();

        splitPanel = new HorizontalSplitPanel();

        VerticalLayout innerLayout = new VerticalLayout();
        Accordion accordion = new Accordion();
        //accordion.addSelectedTabChangeListener(event -> { getViewComponent(); });

        // begin adding the first component
        // search-menu has to have a reference to the workspace in order to be able to add components to it
        wikiSearchMenu = new WikiSearchMenu();
        injector.injectMembers(wikiSearchMenu);
        wikiSearchMenu.initialize();
        accordion.addTab(wikiSearchMenu, wikiSearchMenu.getCaptionTitle(), wikiSearchMenu.getMenuIcon().getSource());

        financeMenu = new FinanceMenu();
        injector.injectMembers(financeMenu);
        financeMenu.initialize();
        //financeMenu.setSizeUndefined();
        accordion.addTab(financeMenu, financeMenu.getCaptionTitle(), financeMenu.getMenuIcon().getSource());

        procedureMenu = new ProcedureMenu();
        injector.injectMembers(procedureMenu);
        procedureMenu.initialize();
        //procedureMenu.setSizeUndefined();
        accordion.addTab(procedureMenu, procedureMenu.getCaptionTitle(), procedureMenu.getMenuIcon().getSource());

        resourceMenu = new ResourceMenu();
        injector.injectMembers(resourceMenu);
        resourceMenu.initialize();
        //resourceMenu.setSizeUndefined();
        accordion.addTab(resourceMenu, resourceMenu.getCaptionTitle(), resourceMenu.getMenuIcon().getSource());

        innerLayout.addComponent(accordion);

        // add a new tab to workspace
        Button addTabButton = new Button("Add New Tab");
        addTabButton.setStyleName("link");
        addTabButton.addClickListener(clickEvent -> onAddTab());
        innerLayout.addComponent(addTabButton);

        workspace = new Workspace();
        injector.injectMembers(workspace);
        splitPanel.setSecondComponent(workspace);

        innerLayout.setWidth("100%");
        splitPanel.setFirstComponent(innerLayout);
        splitPanel.getFirstComponent().setVisible(true);

        addComponent(splitPanel);

        initialized = true;
    }

    /**
     * listener method for adding a new tab to workspace
     */
    public void onAddTab() {
        workspace.addNewTab();
        if (debug) {
            Notification.show("Adding new tab");
        }
    }
}
