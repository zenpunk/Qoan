package qube.qoan.gui.components.common;

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import qube.qoan.gui.views.ComponentsView;
import qube.qoan.gui.views.ManagementView;
import qube.qoan.gui.views.StartView;
import qube.qoan.gui.views.WorkspaceView;

/**
 * Created by rainbird on 11/9/15.
 */
public class QoanHeader extends Panel {

    public QoanHeader() {
        super();

        initialize();
    }

    private void initialize() {

        HorizontalLayout layout = new HorizontalLayout();

        // add some space before the icon
        Label space = new Label("&nbsp;");
        space.setContentMode(ContentMode.HTML);
        layout.addComponent(space);
        // Image as a file resource
        ClassResource resource = new ClassResource("/images/kokoline.gif");
        Image image = new Image("Qoan", resource);
        image.setWidth("20px");
        image.setHeight("30px");
        layout.addComponent(image);

        // button for navigating to Welcome page
        Button homeButton = new Button("Home", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!UI.getCurrent().getEmbedId().equals(StartView.NAME)) {
                    UI.getCurrent().getNavigator().navigateTo(StartView.NAME);
                }
            }
        });
        homeButton.setStyleName("link");
        layout.addComponent(homeButton);

        // button for navigating to the Workspace
        Button workspaceButton = new Button("Workspace", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!UI.getCurrent().getEmbedId().equals(WorkspaceView.NAME)) {
                    UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);
                }
            }
        });
        workspaceButton.setStyleName("link");
        layout.addComponent(workspaceButton);

        // button for navigating to the Components view
        Button componentsButton = new Button("Components", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!UI.getCurrent().getEmbedId().equals(ComponentsView.NAME)) {
                    UI.getCurrent().getNavigator().navigateTo(ComponentsView.NAME);
                }
            }
        });
        componentsButton.setStyleName("link");
        layout.addComponent(componentsButton);

        // button for navigating to the Components view
        Button managementButton = new Button("Management", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!UI.getCurrent().getEmbedId().equals(ManagementView.NAME)) {
                    UI.getCurrent().getNavigator().navigateTo(ManagementView.NAME);
                }
            }
        });
        managementButton.setStyleName("link");
        layout.addComponent(managementButton);

        setContent(layout);
    }
}
