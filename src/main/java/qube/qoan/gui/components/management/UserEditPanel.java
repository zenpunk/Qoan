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

package qube.qoan.gui.components.management;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.authentication.UserManagerInterface;
import qube.qoan.authentication.UserNotAuthenticatedException;
import qube.qoan.gui.views.UserData;
import qube.qoan.gui.views.WorkspaceView;

import javax.inject.Inject;

public class UserEditPanel extends Panel {

    private Logger logger = LoggerFactory.getLogger("UserEditPanel");

    private TextField userField;

    private TextField emailField;

    private PasswordField passwordField;

    private PasswordField passwordCheckField;

    private UserData userData;

    private Binder<UserData> binder;

    @Inject
    private UserManagerInterface userManager;

    public UserEditPanel(UserData userData) {

        super();
        this.userData = userData;
        binder = new Binder<>();

        VerticalLayout layout = new VerticalLayout();
        userField = new TextField("Username");
        binder.forField(userField)
                .withValidator(new StringLengthValidator("Username has to be longer than six characters", 0, 20))
                .bind(UserData::getUsername, UserData::setUsername);
        layout.addComponent(userField);

        emailField = new TextField("E-Mail");
        binder.forField(emailField)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .bind(UserData::getEmail, UserData::setEmail);
        layout.addComponent(emailField);

        passwordField = new PasswordField("Password");
        binder.forField(passwordField)
                .bind(UserData::getPassword, UserData::setPassword);
        layout.addComponent(passwordField);

        passwordCheckField = new PasswordField("Password check");
        binder.forField(passwordCheckField)
                .bind(UserData::getPasswordCheck, UserData::setPasswordCheck);
        layout.addComponent(passwordCheckField);

        Button registerButton = new Button("Register");
        registerButton.setStyleName("link");
        registerButton.addClickListener(clickEvent -> onRegisterClicked());
        layout.addComponent(registerButton);

        // Store return date binding so we can re-validate it later
        Binder.BindingBuilder<UserData, String> returnBindingBuilder = binder.forField(emailField);
        Binder.Binding<UserData, String> returnBinder = returnBindingBuilder.bind(UserData::getPassword, UserData::setPassword);

        // Re-validate value changes
        passwordField.addValueChangeListener(event -> returnBinder.validate());
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
            logger.error("User could not be created", e);
        } catch (UserNotAuthenticatedException e) {
            Notification.show("User could not be created: " + e.getMessage());
            logger.error("User could not be created", e);
        }
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
