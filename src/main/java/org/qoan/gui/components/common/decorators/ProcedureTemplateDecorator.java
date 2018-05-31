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

package org.qoan.gui.components.common.decorators;

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.qai.persistence.QaiDataProvider;
import org.qai.procedure.Procedure;
import org.qai.procedure.ProcedureLibraryInterface;
import org.qai.procedure.ProcedureTemplate;
import org.qai.procedure.utils.SelectForEach;
import org.qai.services.ProcedureRunnerInterface;
import org.qai.services.implementation.SearchResult;
import org.qai.user.User;
import org.qoan.QoanUI;

import javax.inject.Inject;

public class ProcedureTemplateDecorator extends BaseDecorator {

    @Inject
    private QaiDataProvider<Procedure> dataProvider;

    @Inject
    private ProcedureLibraryInterface procedureLibrary;

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

            ProcedureTemplate template = findNamedTemplate(toDecorate.getTitle());

            if (template == null) {
                Notification.show("Template: '" + toDecorate.getTitle() + "' not found");
                return;
            }

            procedure = template.createProcedure();

            // assign the current user so that later credentials can be checked prior to execution
            User user = ((QoanUI) QoanUI.getCurrent()).getUser();
            procedure.setUserUUID(user.getUuid());

            Panel description = createProcedureDescription(template, procedure);

            TabSheet content = new TabSheet();
            content.addTab(description, "Description", descIconImage.getSource());

            // @TODO at least for the time being this is all we need?
            if (procedure instanceof SelectForEach) {
                SelectionDecorator decorator = new SelectionDecorator("Drop Selected Items", (SelectForEach) procedure);
                decorator.decorate(toDecorate);
                content.addTab(decorator, decorator.getName(), decorator.getIconImage().getSource());
            }

            setContent(content);
            isInitialized = true;
        }


    }

    private ProcedureTemplate findNamedTemplate(String templateName) {
        ProcedureTemplate template = null;

        for (ProcedureTemplate tmp : procedureLibrary.getTemplateMap().values()) {
            if (templateName.equalsIgnoreCase(tmp.getProcedureName())) {
                template = tmp;
                break;
            }
        }

        return template;
    }

    /**
     * adds the selection procedures to the given procedure-parameters
     * so that they can be assigned.
     *
     * @param template
     * @return
     */
    /*protected Map<String, SelectForAll> attachSelectionProcedures(Procedure template) {

        Map<String, SelectForAll> procedures = new HashMap<>();

        ProcedureInputs inputs = template.getProcedureInputs();
        for (String name : inputs.getInputNames()) {
            if (ProcedureConstants.TARGET_COLLECTION.equalsIgnoreCase(name)) {
                ValueNode targetValue = inputs.getNamedInput(name);
                SelectForAll selection = new SelectForAll(targetValue);
                template.addChild(selection);
                procedures.put(name, selection);
            }
        }

        return procedures;
    }*/

    /**
     * create the panel with the procedure metrics
     *
     * @param procedureTemplate
     * @param procedure
     * @return
     */
    public Panel createProcedureDescription(ProcedureTemplate procedureTemplate, Procedure procedure) {

        String template = "<b>%s :</b> %s";

        Panel panel = new Panel(procedureTemplate.getProcedureName());
        panel.setWidth("800px");
        //panel.setHeight("600px");

        VerticalLayout contentLayout = new VerticalLayout();
        //contentLayout.setWidth("790px");
        //contentLayout.setWidth("450px");

//        Label nameLabel = new Label(String.format(template, "Name", procedureTemplate.getProcedureName()));
//        nameLabel.setContentMode(ContentMode.HTML);
//        contentLayout.addComponent(nameLabel);

        // @TODO make the description value a variable, so that inidvidual descriptions can be assigned to procedures by user
        Label descriptionLabel = new Label(String.format(template, "Description", procedureTemplate.getProcedureDescription()));
        descriptionLabel.setContentMode(ContentMode.HTML);
        descriptionLabel.setWidth("780px");
        contentLayout.addComponent(descriptionLabel);

        Label uuidLabel = new Label(String.format(template, "UUID", procedure.getUuid()));
        uuidLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(uuidLabel);

        User user = ((QoanUI) QoanUI.getCurrent()).getUser();
        procedure.setUser(user);

        Label userLabel = new Label(String.format(template, "Username", user.getUsername()));
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

        // @TODO only for the time being
        saveButton.setVisible(false);

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
            String message = String.format("Procedure %s with uuid: %s has been submitted",
                    procedure.getProcedureName(),
                    procedure.getUuid());
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
