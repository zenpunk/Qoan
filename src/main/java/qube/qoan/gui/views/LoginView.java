package qube.qoan.gui.views;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.authentication.UserManager;
import qube.qoan.authentication.UserNotAuthenticatedException;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/24/15.
 */
public class LoginView extends BaseQoanView {

    private Logger logger = LoggerFactory.getLogger("LoginView");

    public static String NAME = "LoginView";

    @Inject
    private UserManager userManager;

    public LoginView() {
        this.viewTitle = "Qoan Login";
    }

    //    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        UI.getCurrent().getPage().setTitle("Qoan Login");
//
//        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
//        injector.injectMembers(this);
//
//        QoanHeader header = new QoanHeader();
//        addComponent(header);
//
//
//    }

    @Override
    protected void initialize() {
        FormLayout layout = new FormLayout();
        final ObjectProperty<String> username = new ObjectProperty<String>("");
        TextField userField = new TextField("Username", username);
        layout.addComponent(userField);

        final ObjectProperty<String> password = new ObjectProperty<String>("");
        PasswordField passwordField = new PasswordField("Password", password);
        layout.addComponent(passwordField);

        Button loginButton = new Button("Login");
        loginButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                User user = null;
                try {
                    user = userManager.authenticateUser(username.getValue(), password.getValue());
                } catch (UserNotAuthenticatedException e) {
                    logger.error("User: '" + username + "' with password: '" + password + "' cannot be authenticated", e);
                }
                ((QoanUI) UI.getCurrent()).setUser(user);
                String targetPage = ((QoanUI) UI.getCurrent()).getTargetViewName();
                if (targetPage == null) {
                    targetPage = StartView.NAME;
                }
                UI.getCurrent().getNavigator().navigateTo(targetPage);
            }
        });
        layout.addComponent(loginButton);

        addComponent(layout);
        setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
    }
}
