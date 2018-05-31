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

package org.qoan.gui.views;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import org.qai.user.User;
import org.qoan.QoanUI;
import org.qoan.authentication.UserManagerInterface;
import org.qoan.authentication.UserNotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class RegistrationView extends QoanView {

    private Logger logger = LoggerFactory.getLogger("RegistrationView");

    public static String NAME = "RegistrationView";

    private TextField userField;

    private TextField emailField;

    private PasswordField passwordField;

    private PasswordField passwordCheckField;

    private UserData userData;

    Binder<UserData> binder;

    @Inject
    private UserManagerInterface userManager;

    public RegistrationView() {
        this.viewTitle = "Qoan Registration";
    }

    @Override
    protected void initialize() {

        binder = new Binder<>();
        userData = new UserData();

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setWidth("80%");
        ClassResource resource = new ClassResource("gui/images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        image.setWidth("30%");
        firstRow.addComponent(image);

        VerticalLayout layout = new VerticalLayout();
        userField = new TextField("Username");
        binder.forField(userField)
                .withValidator(new StringLengthValidator("Username has to be longer than six characters", 0, 20))
                .bind(UserData::getUsername, UserData::setUsername);
        layout.addComponent(userField);

        passwordField = new PasswordField("Password");
        binder.forField(passwordField)
                .bind(UserData::getPassword, UserData::setPassword);
        layout.addComponent(passwordField);

        passwordCheckField = new PasswordField("Password check");
        binder.forField(passwordCheckField)
                .bind(UserData::getPasswordCheck, UserData::setPasswordCheck);
        layout.addComponent(passwordCheckField);

        emailField = new TextField("E-Mail");
        binder.forField(emailField)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .bind(UserData::getEmail, UserData::setEmail);
        layout.addComponent(emailField);

        Button registerButton = new Button("Register");
        registerButton.setStyleName("link");
        registerButton.addClickListener(clickEvent -> onRegisterClicked());
        layout.addComponent(registerButton);

        // Store return date binding so we can re-validate it later
        Binder.BindingBuilder<UserData, String> returnBindingBuilder = binder.forField(emailField);
        Binder.Binding<UserData, String> returnBinder = returnBindingBuilder.bind(UserData::getEmail, UserData::setEmail);

        // Re-validate value changes
        passwordField.addValueChangeListener(event -> returnBinder.validate());

        firstRow.addComponent(layout);
        addComponent(firstRow);
    }

    public void onRegisterClicked() {

        try {

            binder.writeBean(userData);

            if (!userData.getPassword().equals(userData.getPasswordCheck())) {
                Notification.show("Passwords are not equal");
                return;
            }

            User user = userManager.createUser(userData.getUsername(), userData.getPassword(), "User", "toExecuteProcedures");
            ((QoanUI) UI.getCurrent()).setUser(user);
            userManager.authenticateUser(userData.getUsername(), userData.getPassword());
            UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);

        } catch (ValidationException e) {
            Notification.show("User could not be created: " + e.getMessage());
        } catch (UserNotAuthenticatedException e) {
            Notification.show("User could not be created: " + e.getMessage());
        } finally {
            userData = new UserData();
        }
    }
}
