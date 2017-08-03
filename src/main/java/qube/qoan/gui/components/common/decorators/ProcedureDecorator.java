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
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.ProcedureLibrary;
import qube.qai.procedure.ProcedureTemplate;
import qube.qai.services.implementation.SearchResult;
import qube.qai.user.User;
import qube.qoan.QoanUI;

import javax.inject.Inject;

public class ProcedureDecorator extends BaseDecorator {

    @Inject
    private QaiDataProvider<Procedure> dataProvider;

    private Procedure procedure;

    private Image iconImage;

    public ProcedureDecorator() {
        iconImage = new Image("Procedure",
                new ClassResource("gui/images/proc.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        if (StringUtils.isNotBlank(toDecorate.getUuid())) {
            procedure = dataProvider.brokerSearchResult(toDecorate);
        } else {
            ProcedureTemplate template = ProcedureLibrary.getNamedProcedureTemplate(toDecorate.getTitle());
            if (template == null) {
                Notification.show("Template: '" + toDecorate.getTitle() + "' not found");
                return;
            }
            procedure = template.createProcedure();
        }

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setCaption(toDecorate.getTitle());

        Label descriptionLabel = new Label("Description: " + procedure.getDescriptionText());
        contentLayout.addComponent(descriptionLabel);

        Label uuidLabel = new Label("UUID: " + procedure.getUuid());
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
        Label userLabel = new Label("Username: " + procedure.getUser().getUsername());
        contentLayout.addComponent(userLabel);

        Label hasExecutedLabel = new Label("Has executed: " + procedure.hasExecuted());
        contentLayout.addComponent(hasExecutedLabel);

        Label durationLabel = new Label("Duration: " + procedure.getDuration());
        contentLayout.addComponent(durationLabel);

        Label percentageLabel = new Label("Execution percentage: " + procedure.getProgressPercentage() + "%");
        contentLayout.addComponent(percentageLabel);

        Button saveButton = new Button("Save Procedure");
        saveButton.addClickListener(event -> onSaveProcedure());
        saveButton.setStyleName("link");
        contentLayout.addComponent(saveButton);

        setContent(contentLayout);

    }

    public void onSaveProcedure() {
        if (procedure != null) {
            dataProvider.putData(procedure.getUuid(), procedure);
        }
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }
}