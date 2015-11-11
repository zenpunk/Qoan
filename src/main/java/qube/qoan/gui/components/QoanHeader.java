package qube.qoan.gui.components;

import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import qube.qoan.gui.views.ComponentsView;
import qube.qoan.gui.views.StartView;
import qube.qoan.gui.views.WorkspaceView;

import java.io.File;

/**
 * Created by rainbird on 11/9/15.
 */
public class QoanHeader extends Panel {

    public QoanHeader() {
        super();

        initialize();
    }

    private void initialize() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");

        CssLayout firstRow = new CssLayout();

        // Image as a file resource
        ClassResource resource = new ClassResource("/images/crows.jpg");
        Image image = new Image("qube.qoan", resource);

        firstRow.addComponent(image);
        layout.addComponent(firstRow);

        HorizontalLayout secondRow = new HorizontalLayout();

        Button homeButton = new Button("Home", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(StartView.NAME);
            }
        });

        secondRow.addComponent(homeButton);

        Button workspaceButton = new Button("Workspace", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);
            }
        });

        secondRow.addComponent(workspaceButton);

        Button componentsButton = new Button("Components", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(ComponentsView.NAME);
            }
        });

        secondRow.addComponent(componentsButton);
        layout.addComponent(secondRow);
        setContent(layout);
    }
}
