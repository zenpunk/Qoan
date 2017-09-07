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

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.authentication.UserManagerInterface;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/24/15.
 */
public class LoginView extends QoanView {

    private Logger logger = LoggerFactory.getLogger("LoginView");

    public static String NAME = "LoginView";

    private TextField userField;

    private TextField emailField;

    private PasswordField passwordField;

    private PasswordField passwordCheckField;

    private UserData userData;

    Binder<UserData> binder;

    @Inject
    private UserManagerInterface userManager;

    public LoginView() {
        this.viewTitle = "Qoan Login";
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
                .bind(UserData::getUsername, UserData::setUsername);
        layout.addComponent(userField);

        emailField = new TextField("E-Mail");
        binder.forField(emailField)
                .bind(UserData::getEmail, UserData::setEmail);
        emailField.setVisible(false);
        layout.addComponent(emailField);

        passwordField = new PasswordField("Password");
        binder.forField(passwordField)
                .bind(UserData::getPassword, UserData::setPassword);
        layout.addComponent(passwordField);

        passwordCheckField = new PasswordField("Password check");
        passwordCheckField.setVisible(false);
        binder.forField(passwordCheckField)
                .bind(UserData::getPasswordCheck, UserData::setPasswordCheck);
        layout.addComponent(passwordCheckField);

        HorizontalLayout buttonRow = new HorizontalLayout();

        Button loginButton = new Button("Login");
        loginButton.setStyleName("link");
        loginButton.addClickListener(event -> onLoginClicked());
        buttonRow.addComponent(loginButton);

        Button registerButton = new Button("Register");
        registerButton.setStyleName("link");
        registerButton.addClickListener(clickEvent -> onRegisterClicked());
        buttonRow.addComponent(registerButton);

        Button guestLoginButton = new Button("Login as Guest user");
        guestLoginButton.setStyleName("link");
        guestLoginButton.addClickListener(event -> onGuestLoginClicked());
        buttonRow.addComponent(guestLoginButton);

        layout.addComponent(buttonRow);
        firstRow.addComponent(layout);
        addComponent(firstRow);

    }

    public void onGuestLoginClicked() {

    }

    public void onLoginClicked() {

        User user;
        if (StringUtils.isNoneBlank(userField.getValue())) {
            user = new User(userField.getValue(), passwordField.getValue());
        } else {
            user = new User("sa", "");
        }

        ((QoanUI) UI.getCurrent()).setUser(user);
        String targetPage = ((QoanUI) UI.getCurrent()).getTargetViewName();

        if (targetPage == null) {
            targetPage = StartView.NAME;
        }

        UI.getCurrent().getNavigator().navigateTo(targetPage);

    }

    public void onRegisterClicked() {

        try {

            binder.writeBean(userData);

            User user = userManager.createUser(userData.getUsername(), userData.getPassword(), "User", "toExecuteProcedures");


        } catch (ValidationException e) {
            Notification.show("User could not be created, " +
                    "please check error messages for each field.");
        }
    }

}
