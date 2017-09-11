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

import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

    public static String GUEST_USERNAME = "Guest";

    public static String GUEST_PASSWORD = "password";

    private TextField userField;

    private PasswordField passwordField;

    @Inject
    private UserManagerInterface userManager;

    public LoginView() {
        this.viewTitle = "Qoan Login";
    }

    @Override
    protected void initialize() {

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setWidth("80%");
        ClassResource resource = new ClassResource("gui/images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        image.setWidth("30%");
        firstRow.addComponent(image);

        VerticalLayout layout = new VerticalLayout();
        userField = new TextField("Username");
        layout.addComponent(userField);

        passwordField = new PasswordField("Password");
        layout.addComponent(passwordField);

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

        User user = userManager.findUser(GUEST_USERNAME);
        if (user == null) {
            user = userManager.createUser(GUEST_USERNAME, GUEST_PASSWORD, "GuestRole", "View_only_permission");
        }

        try {
            userManager.authenticateUser(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            logger.error("There has to be a guest user!", e);
            return;
        }
    }

    public void onLoginClicked() {

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {

            UsernamePasswordToken token = new UsernamePasswordToken(userField.getValue(), passwordField.getValue());

            try {
                subject.login(token);
                User user = (User) subject.getPrincipal();
                ((QoanUI) UI.getCurrent()).setUser(user);
                String targetPage = ((QoanUI) UI.getCurrent()).getTargetViewName();

                if (targetPage == null) {
                    targetPage = StartView.NAME;
                }

                UI.getCurrent().getNavigator().navigateTo(targetPage);

            } catch (AuthenticationException e) {
                Notification.show("User cannot login with username and password supplied");
            }
        }

    }

    public void onRegisterClicked() {
        ((QoanUI) UI.getCurrent()).setTargetViewName(RegistrationView.NAME);
        UI.getCurrent().getNavigator().navigateTo(RegistrationView.NAME);
    }

}
