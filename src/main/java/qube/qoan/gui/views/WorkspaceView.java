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
import qube.qoan.gui.components.workspace.document.DocumentMenu;
import qube.qoan.gui.components.workspace.finance.FinanceMenu;
import qube.qoan.gui.components.workspace.procedure.ProcedureMenu;
import qube.qoan.gui.components.workspace.search.SearchMenu;

//import com.vaadin.pekka.resizablecsslayout.ResizableCssLayout;

/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends QoanView {

    private boolean initialized = false;

    private boolean debug = true;

    public static String NAME = "workspace";

    private SearchMenu searchMenu;

    private FinanceMenu financeMenu;

    private ProcedureMenu procedureMenu;

    private DocumentMenu documentMenu;

    private Workspace workspace;

    private HorizontalSplitPanel splitPanel;

    private Component currentComponent;

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

        // begin adding the first component
        // search-menu has to have a reference to the workspace in order to be able to add components to it
        searchMenu = new SearchMenu();
        injector.injectMembers(searchMenu);
        searchMenu.initialize();
        searchMenu.setSizeUndefined();

        financeMenu = new FinanceMenu();
        injector.injectMembers(financeMenu);
        financeMenu.setSizeUndefined();

        procedureMenu = new ProcedureMenu();
        injector.injectMembers(procedureMenu);
        procedureMenu.setSizeUndefined();

        documentMenu = new DocumentMenu();
        injector.injectMembers(documentMenu);
        documentMenu.setSizeUndefined();

        workspace = new Workspace();
        injector.injectMembers(workspace);
        splitPanel.setSecondComponent(workspace);

        currentComponent = searchMenu;
        currentComponent.setWidth("100%");
        splitPanel.setFirstComponent(currentComponent);
        splitPanel.getFirstComponent().setVisible(true);

        addComponent(splitPanel);

        HorizontalLayout lowerLayout = new HorizontalLayout();

        // add a new tab to workspace
        Button addTabButton = new Button("Add New Tab");
        addTabButton.setStyleName("link");
        addTabButton.addClickListener(clickEvent -> onAddTab());
        lowerLayout.addComponent(addTabButton);

        // open the search menu
        Button showSearchMenuButton = new Button("Show Search Menu");
        showSearchMenuButton.setStyleName("link");
        showSearchMenuButton.addClickListener(clickEvent -> onShowSearch());
        lowerLayout.addComponent(showSearchMenuButton);

        // open the procedure menu
        Button showProcedureMenuButton = new Button("Show Procedure Menu");
        showProcedureMenuButton.setStyleName("link");
        showProcedureMenuButton.addClickListener(clickEvent -> onShowProcedure());
        lowerLayout.addComponent(showProcedureMenuButton);

        // open the finance-menu
        Button showFinanceMenuButton = new Button("Show Finance Menu");
        showFinanceMenuButton.setStyleName("link");
        showFinanceMenuButton.addClickListener(clickEvent -> onShowFinance());
        lowerLayout.addComponent(showFinanceMenuButton);

        Button showResourceMenuButton = new Button("Show Finance Menu");
        showResourceMenuButton.setStyleName("link");
        showFinanceMenuButton.addListener(event -> onShowResource());
        lowerLayout.addComponent(showFinanceMenuButton);


        addComponent(lowerLayout);

        initialized = true;
    }

    /**
     * listener method for showing resource-menu
     */
    public void onShowResource() {
        splitPanel.removeComponent(currentComponent);
        documentMenu.initialize();
        currentComponent = documentMenu;
        currentComponent.setSizeFull();
        splitPanel.setFirstComponent(documentMenu);
        if (debug) {
            Notification.show("Show process-menu");
        }
    }

    /**
     * listener method for showing procedure-menu
     */
    public void onShowProcedure() {
        splitPanel.removeComponent(currentComponent);
        procedureMenu.initialize();
        currentComponent = procedureMenu;
        currentComponent.setSizeFull();
        splitPanel.setFirstComponent(procedureMenu);
        if (debug) {
            Notification.show("Show process-menu");
        }
    }

    /**
     * listener method for showing market-menu
     */
    public void onShowFinance() {
        splitPanel.removeComponent(currentComponent);
        financeMenu.initialize();
        currentComponent = financeMenu;
        currentComponent.setSizeFull();
        splitPanel.setFirstComponent(financeMenu);
        if (debug) {
            Notification.show("Show finance-menu");
        }
    }

    /**
     * listener method for showing search-menu
     */
    public void onShowSearch() {
        splitPanel.removeComponent(currentComponent);
        searchMenu.initialize();
        currentComponent = searchMenu;
        currentComponent.setSizeFull();
        splitPanel.setFirstComponent(searchMenu);
        if (debug) {
            Notification.show("Show search menu");
        }
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
