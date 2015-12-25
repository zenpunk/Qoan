package qube.qoan.gui.views;

import com.google.inject.Injector;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.authentication.UserManager;
import qube.qoan.gui.components.common.QoanHeader;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/24/15.
 */
public class LoginView extends VerticalLayout implements View {

    public static String NAME = "LoginView";

    @Inject
    private UserManager userManager;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        UI.getCurrent().getPage().setTitle("Qoan Login");

        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
        injector.injectMembers(this);

        QoanHeader header = new QoanHeader();
        addComponent(header);

        FormLayout layout = new FormLayout();
        ObjectProperty<String> username = new ObjectProperty<String>("");
        TextField userField = new TextField("Username", username);
        layout.addComponent(userField);

        ObjectProperty<String> password = new ObjectProperty<String>("");
        PasswordField passwordField = new PasswordField("Password", password);
        layout.addComponent(passwordField);

        Button loginButton = new Button("Login");
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                User user = userManager.authenticateUser(username.getValue(), password.getValue());
                if (user != null) {
                    ((QoanUI) UI.getCurrent()).setUser(user);
                    String targetPage = ((QoanUI) UI.getCurrent()).getTargetViewName();
                    if (targetPage == null) {
                        targetPage = StartView.NAME;
                    }
                    UI.getCurrent().getNavigator().navigateTo(targetPage);
                }
            }
        });
        layout.addComponent(loginButton);

        addComponent(layout);
        setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
    }

}
