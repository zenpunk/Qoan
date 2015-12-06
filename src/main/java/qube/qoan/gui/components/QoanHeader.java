package qube.qoan.gui.components;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import qube.qoan.gui.views.ComponentsView;
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

        HorizontalLayout secondRow = new HorizontalLayout();

        // Image as a file resource
        ClassResource resource = new ClassResource("/images/kokoline.gif");
        Image image = new Image("Qoan", resource);
        image.setWidth("20px");
        image.setHeight("30px");
        secondRow.addComponent(image);

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
        secondRow.addComponent(homeButton);

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
        secondRow.addComponent(workspaceButton);

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
        secondRow.addComponent(componentsButton);

        setContent(secondRow);
    }
}
