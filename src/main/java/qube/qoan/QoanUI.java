package qube.qoan;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import qube.qoan.gui.views.ComponentsView;
import qube.qoan.gui.views.StartView;
import qube.qoan.gui.views.WorkspaceView;

/**
 *
 */
@Theme("mytheme")
@Widgetset("qube.qoan.MyAppWidgetset")
public class QoanUI extends UI {

    Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Qoan");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views- not that this way, the pages will always be new instances!
        navigator.addView("", StartView.class);
        navigator.addView(ComponentsView.NAME, ComponentsView.class);
        navigator.addView(WorkspaceView.NAME, new WorkspaceView());
    }



}
