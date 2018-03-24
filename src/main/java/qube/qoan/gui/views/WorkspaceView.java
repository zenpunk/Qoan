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

package qube.qoan.gui.views;

import com.google.inject.Injector;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import qube.qoan.gui.components.workspace.Workspace;
import qube.qoan.gui.components.workspace.finance.FinanceMenu;
import qube.qoan.gui.components.workspace.procedure.ProcedureMenu;
import qube.qoan.gui.components.workspace.resource.ResourceMenu;
import qube.qoan.gui.components.workspace.wiki.WikiMenu;
import qube.qoan.services.QoanInjectorService;


/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends QoanView {

    private boolean initialized = false;

    private boolean debug = true;

    public static String NAME = "workspace";

    private Workspace workspace;

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
        //Injector injector = ((QoanUI) QoanUI.getCurrent()).getInjector();
        Injector injector = QoanInjectorService.getInstance().getInjector();

        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        splitPanel.setSplitPosition(35, Unit.PERCENTAGE);

        //VerticalLayout innerLayout = new VerticalLayout();
        Accordion accordion = new Accordion();

        // begin adding the first component
        // wiki-menu has to have a reference to the workspace in order to be able to add components to it
        WikiMenu wikiMenu = new WikiMenu();
        injector.injectMembers(wikiMenu);
        wikiMenu.initialize();
        accordion.addTab(wikiMenu, wikiMenu.getCaptionTitle(), wikiMenu.getMenuIcon().getSource());

        FinanceMenu financeMenu = new FinanceMenu();
        injector.injectMembers(financeMenu);
        financeMenu.initialize();
        accordion.addTab(financeMenu, financeMenu.getCaptionTitle(), financeMenu.getMenuIcon().getSource());

        ResourceMenu resourceMenu = new ResourceMenu();
        injector.injectMembers(resourceMenu);
        resourceMenu.initialize();
        accordion.addTab(resourceMenu, resourceMenu.getCaptionTitle(), resourceMenu.getMenuIcon().getSource());

        ProcedureMenu procedureMenu = new ProcedureMenu();
        injector.injectMembers(procedureMenu);
        procedureMenu.initialize();
        accordion.addTab(procedureMenu, procedureMenu.getCaptionTitle(), procedureMenu.getMenuIcon().getSource());

        splitPanel.setFirstComponent(accordion);

        workspace = new Workspace();
        injector.injectMembers(workspace);
        splitPanel.setSecondComponent(workspace);

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
