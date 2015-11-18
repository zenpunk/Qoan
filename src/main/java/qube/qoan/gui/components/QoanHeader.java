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
        firstRow.addStyleName("header");

        // Image as a file resource
//        ClassResource resource = new ClassResource("/images/crows.jpg");
//        Image image = new Image("qube.qoan", resource);
//
//        firstRow.addComponent(image);
//        parentLayout.addComponent(firstRow);

        HorizontalLayout secondRow = new HorizontalLayout();

        Button homeButton = new Button("Home", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(StartView.NAME);
            }
        });
        homeButton.setStyleName("link");
        secondRow.addComponent(homeButton);

        Button workspaceButton = new Button("Workspace", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);
            }
        });
        workspaceButton.setStyleName("link");
        secondRow.addComponent(workspaceButton);

        Button componentsButton = new Button("Components", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(ComponentsView.NAME);
            }
        });
        componentsButton.setStyleName("link");
        secondRow.addComponent(componentsButton);
        layout.addComponent(secondRow);
        setContent(layout);
    }
}
