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

package qube.qoan.gui.components.common.decorators;

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.ProcedureLibrary;
import qube.qai.procedure.ProcedureTemplate;
import qube.qai.procedure.nodes.ProcedureInputs;
import qube.qai.procedure.nodes.ValueNode;
import qube.qai.procedure.utils.SelectionProcedure;
import qube.qai.services.ProcedureRunnerInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qai.user.User;
import qube.qoan.QoanUI;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ProcedureTemplateDecorator extends BaseDecorator {

    @Inject
    private QaiDataProvider<Procedure> dataProvider;

    @Inject
    private ProcedureRunnerInterface procedureRunner;

    private Procedure procedure;

    private Image iconImage;

    private Image descIconImage;

    private Button saveButton;

    private Button startButton;

    private boolean isSaved = false;

    private boolean isInitialized = false;

    public ProcedureTemplateDecorator() {
        iconImage = new Image("Procedure",
                new ClassResource("gui/images/proc-icon.png"));
        descIconImage = new Image("",
                new ClassResource("gui/images/readings-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        if (!isInitialized) {

            ProcedureTemplate template = ProcedureLibrary.getTemplateNameMap().get(toDecorate.getTitle());

            if (template == null) {
                Notification.show("Template: '" + toDecorate.getTitle() + "' not found");
                return;
            }

            procedure = template.createProcedure();

            Panel description = createProcedureDescription(procedure);

            TabSheet content = new TabSheet();
            content.addTab(description, "Description", descIconImage.getSource());

            Map<String, SelectionProcedure> children = attachSelectionProcedures(procedure);
            if (children.size() > 0) {
                TabSheet tabSheet;
                for (String name : children.keySet()) {
                    SelectionProcedure child = children.get(name);
                    SelectionDecorator decorator = new SelectionDecorator(name, child);
                    decorator.decorate(toDecorate);
                    content.addTab(decorator, decorator.getName(), decorator.getIconImage().getSource());
                }
            }

            setContent(content);
            isInitialized = true;
        }


    }

    /**
     * adds the selection procedures to the given procedure-parameters
     * so that they can be assigned.
     *
     * @param template
     * @return
     */
    protected Map<String, SelectionProcedure> attachSelectionProcedures(Procedure template) {

        Map<String, SelectionProcedure> procedures = new HashMap<>();

        ProcedureInputs inputs = template.getProcedureInputs();
        for (String name : inputs.getInputNames()) {
            ValueNode targetValue = inputs.getNamedInput(name);
            SelectionProcedure selection = new SelectionProcedure(targetValue);
            template.addChild(selection);
            procedures.put(name, selection);
        }

        return procedures;
    }

    /**
     * create the panel with the procedure metrics
     *
     * @param procedure
     * @return
     */
    public Panel createProcedureDescription(Procedure procedure) {

        String template = "<b> %s :</b><i> %s </i>";

        Panel panel = new Panel(procedure.getProcedureName());

        VerticalLayout contentLayout = new VerticalLayout();

        Label descriptionLabel = new Label(String.format(template, "Description", procedure.getDescriptionText()));
        descriptionLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(descriptionLabel);

        Label uuidLabel = new Label(String.format(template, "UUID", procedure.getUuid()));
        uuidLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(uuidLabel);

        String userName = "User not assigned";
        if (procedure.getUser() == null) {
            User user = ((QoanUI) QoanUI.getCurrent()).getUser();
            if (user != null) {
                procedure.setUser(user);
            } else {
                procedure.setUser(new User(userName, ""));
            }
        }
        Label userLabel = new Label(String.format(template, "Username", procedure.getUser().getUsername()));
        userLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(userLabel);

        Label hasExecutedLabel = new Label(String.format(template, "Has executed", procedure.hasExecuted()));
        hasExecutedLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(hasExecutedLabel);

        Label durationLabel = new Label(String.format(template, "Duration", procedure.getDuration()));
        durationLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(durationLabel);

        Label percentageLabel = new Label(String.format(template, "Execution percentage", procedure.getProgressPercentage() + "%"));
        percentageLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(percentageLabel);

        HorizontalLayout buttonRow = new HorizontalLayout();
        saveButton = new Button("Save Procedure");
        saveButton.addClickListener(event -> onSaveProcedure());
        saveButton.setStyleName("link");
        buttonRow.addComponent(saveButton);

        startButton = new Button("Start Procedure");
        startButton.addClickListener(clickEvent -> onStartProcedure());
        startButton.setStyleName("link");
        startButton.setVisible(isSaved);
        buttonRow.addComponent(startButton);
        contentLayout.addComponent(buttonRow);

        panel.setContent(contentLayout);

        return panel;
    }

    /**
     * button action for saving procedures
     */
    public void onSaveProcedure() {
        if (procedure != null) {
            dataProvider.putData(procedure.getUuid(), procedure);
            isSaved = true;
            startButton.setVisible(true);
        }
    }

    public void onStartProcedure() {
        if (procedure != null && isSaved) {
            procedureRunner.submitProcedure(procedure);
            String message = String.format("Procedure %s with uuid: %s has been submitted by user: %s",
                    procedure.getProcedureName(),
                    procedure.getUuid(),
                    procedure.getUser().getUsername());
            logger.info(message);
            Notification.show(message);
        }
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public String getName() {
        return "Procedure Decorator";
    }
}
