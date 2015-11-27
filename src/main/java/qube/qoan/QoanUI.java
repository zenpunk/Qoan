package qube.qoan;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import qube.qai.main.QaiModule;
import qube.qoan.gui.views.ComponentsView;
import qube.qoan.gui.views.StartView;
import qube.qoan.gui.views.WorkspaceView;
import qube.qoan.services.QoanModule;

/**
 *
 */
@Theme("mytheme")
@Widgetset("qube.qoan.MyAppWidgetset")
public class QoanUI extends UI {

    protected Navigator navigator;

    protected WorkspaceView workspaceView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Injector injector = Guice.createInjector(new ServletModule(), new QoanModule(), new QaiModule());

        getPage().setTitle("Qoan");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // instantiate the workspace-view
        if (workspaceView == null) {
            workspaceView = new WorkspaceView();
        }

        // Create and register the views- not that this way, the pages will always be new instances!
        navigator.addView("", StartView.class);
        navigator.addView(ComponentsView.NAME, ComponentsView.class);
        navigator.addView(WorkspaceView.NAME, workspaceView);
    }



}
