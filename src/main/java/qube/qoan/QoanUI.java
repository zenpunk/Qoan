package qube.qoan;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import qube.qai.main.QaiModule;
import qube.qai.user.User;
import qube.qoan.authentication.SecureViewChangeListener;
import qube.qoan.gui.views.*;
import qube.qoan.services.QoanModule;

/**
 *
 */
@Theme("mytheme")
@Widgetset("qube.qoan.MyAppWidgetset")
@JavaScript({"js/mathjax/MathJax.js", "js/jspdf/jspdf.min.js"})
public class QoanUI extends UI {

    protected Navigator navigator;

    protected SecureViewChangeListener changeListener;

    protected WorkspaceBaseView workspaceView;

    protected ManagementBaseView managementView;

    protected Injector injector;

    protected User user;

    protected String targetViewName;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        // this way we have a different injector for each thread
        injector = Guice.createInjector(new QoanModule(), new QaiModule());

        getPage().setTitle("Qoan");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);
        changeListener = new SecureViewChangeListener();
        navigator.addViewChangeListener(changeListener);

        // instantiate the workspace-view
        if (workspaceView == null) {
            workspaceView = new WorkspaceBaseView();
        }

        // instantiate the management-view,
        // so that the thing has time to create its listeners and things
        if (managementView == null) {
            managementView = new ManagementBaseView();
            injector.injectMembers(managementView);
        }

        // Create and register the views
        navigator.addView("", StartBaseView.class);
        navigator.addView(LoginBaseView.NAME, LoginBaseView.class);
        navigator.addView(ComponentsBaseView.NAME, ComponentsBaseView.class);
        navigator.addView(WorkspaceBaseView.NAME, workspaceView);
        navigator.addView(ManagementBaseView.NAME, managementView);
        navigator.addView(WikiBaseView.NAME, WikiBaseView.class);
    }

    public Injector getInjector() {
        return injector;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTargetViewName() {
        return targetViewName;
    }

    public void setTargetViewName(String targetViewName) {
        this.targetViewName = targetViewName;
    }
}
