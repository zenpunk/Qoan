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
import org.qai.services.implementation.SearchResult;
import org.qai.user.User;
import org.qoan.QoanUI;

import javax.inject.Inject;

public class ProcedureDecorator extends BaseDecorator {

    private Image iconImage;

    private Image descIconImage;

    private String name = "Procedure";

    @Inject
    private QaiDataProvider<Procedure> dataProvider;

    public ProcedureDecorator() {
        iconImage = new Image(name,
                new ClassResource("gui/images/proc-icon.png"));
        descIconImage = new Image("",
                new ClassResource("gui/images/readings-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {
        Procedure procedure = dataProvider.brokerSearchResult(toDecorate);

        Panel description = createProcedureDescription(procedure);
        TabSheet content = new TabSheet();
        content.addTab(description, "Description", descIconImage.getSource());

        // @TODO here come the decorators for the results.

        setContent(content);
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
        if (procedure.getUserUUID() == null) {
            User user = ((QoanUI) QoanUI.getCurrent()).getUser();
            if (user != null) {
                procedure.setUser(user);
            } else {
                procedure.setUser(new User(userName, ""));
            }
        }

//        Label userLabel = new Label(String.format(template, "Username", procedure.getUser().getUsername()));
//        userLabel.setContentMode(ContentMode.HTML);
//        contentLayout.addComponent(userLabel);

        Label hasExecutedLabel = new Label(String.format(template, "Has executed", procedure.hasExecuted()));
        hasExecutedLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(hasExecutedLabel);

        Label durationLabel = new Label(String.format(template, "Duration", procedure.getDuration()));
        durationLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(durationLabel);

        Label percentageLabel = new Label(String.format(template, "Execution percentage", procedure.getProgressPercentage() + "%"));
        percentageLabel.setContentMode(ContentMode.HTML);
        contentLayout.addComponent(percentageLabel);

        panel.setContent(contentLayout);

        return panel;
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public String getName() {
        return name;
    }
}
