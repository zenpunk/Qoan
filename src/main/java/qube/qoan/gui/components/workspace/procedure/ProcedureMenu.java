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

package qube.qoan.gui.components.workspace.procedure;

import com.google.inject.Injector;
import com.vaadin.ui.UI;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.common.QoanMenu;
import qube.qoan.gui.components.common.search.SearchResultSinkComponent;
import qube.qoan.services.ProcedureCache;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureMenu extends QoanMenu {

    @Inject
    private ProcedureCache procedureCache;

    @Inject
    @Named("Procedures")
    private SearchResultSinkComponent resultSink;

    /**
     * this is the container for elements which will be
     * needed to design, run and gain access to results
     * of the procedures which have already run
     * For the moment being though, it will be limited only
     * to display the procedure and its results
     */
    public ProcedureMenu() {

        super();

        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
        injector.injectMembers(this);

        initialize(PROCEDURES);
    }
/*

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();
        //layout.setWidth("300px");

        final ProcedureRepositoryPanel repositoryPanel = new ProcedureRepositoryPanel();
        layout.addComponent(repositoryPanel);

        final ProcedureListPanel procedureListPanel = new ProcedureListPanel();
        // create the button which does the work
        Button showSelectedProcedureButton = new Button("Show selected procedure");
        showSelectedProcedureButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String name = repositoryPanel.getSelectedProcedure();
                Procedure procedure = null;
                // @TODO this is perhaps really only temporary
                // this is what we do when we need to create a dummy procedure
                if (StringUtils.containsIgnoreCase(name, "dummy")) {
                    procedure = new NeuralNetworkAnalysis();
                    procedureCache.cacheProcedure(procedure);
                } else {
                    procedure = procedureCache.getProcedureWithName(name);
                }

                procedureListPanel.displayProcedure(procedure);
            }
        });
        showSelectedProcedureButton.setStyleName("link");
        layout.addComponent(showSelectedProcedureButton);
        layout.addComponent(procedureListPanel);
        setContent(layout);

    }
*/

}
