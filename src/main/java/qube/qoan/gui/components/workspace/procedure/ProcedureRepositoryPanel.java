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
import com.vaadin.ui.*;
import qube.qai.services.ProcedureSourceInterface;
import qube.qoan.QoanUI;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureRepositoryPanel extends Panel {

    @Inject
    private ProcedureSourceInterface procedureSource;
    private String dummyName = "Dummy neural-network analysis";

    //private ObjectProperty<String> name;

    /**
     * this is supposed to read the existing and already
     * defined procedure templates and return the selected
     * one among them to be displayed
     */
    public ProcedureRepositoryPanel() {

        super();

        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
        injector.injectMembers(this);

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();

        Label label = new Label("Procedures in repository");
        layout.addComponent(label);

        // for the time being only one procedure is for selection
        ComboBox procedureSelectBox = new ComboBox("Procedures");
//        name = new ObjectProperty<String>("", String.class);
//        procedureSelectBox.setPropertyDataSource(name);
//        procedureSelectBox.addItem(dummyName);
//        String[] procedureNames = procedureSource.getProcedureNames();
//        for (String name : procedureNames) {
//            procedureSelectBox.addItem(name);
//        }

        layout.addComponent(procedureSelectBox);

        setContent(layout);
    }

    public String getSelectedProcedure() {
//        NeuralNetworkAnalysis neuralNetworkAnalysis = (NeuralNetworkAnalysis) NeuralNetworkAnalysis.Factory.constructProcedure();
//        return neuralNetworkAnalysis;
        //return name.getValue();
        return "";
    }

//    @Override
//    public Procedure getProcedureWithName(String name) {
//        return procedureSource.getProcedureWithName(name);
//    }
}
