package qube.qoan.gui.components.common;

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.gui.views.*;

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
                ((QoanUI) UI.getCurrent()).setTargetViewName(StartView.NAME);
                UI.getCurrent().getNavigator().navigateTo(StartView.NAME);
            }
        });
        homeButton.setStyleName("link");
        layout.addComponent(homeButton);

        // button for navigating to the Workspace
        Button workspaceButton = new Button("Workspace", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((QoanUI)UI.getCurrent()).setTargetViewName(WorkspaceView.NAME);
                UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);
            }
        });
        workspaceButton.setStyleName("link");
        layout.addComponent(workspaceButton);

        // button for navigating to the Components view
        Button componentsButton = new Button("Components", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((QoanUI)UI.getCurrent()).setTargetViewName(ComponentsView.NAME);
                UI.getCurrent().getNavigator().navigateTo(ComponentsView.NAME);
            }
        });
        componentsButton.setStyleName("link");
        layout.addComponent(componentsButton);

        // button for navigating to the Components view
        Button managementButton = new Button("Management", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((QoanUI) UI.getCurrent()).setTargetViewName(ManagementView.NAME);
                UI.getCurrent().getNavigator().navigateTo(ManagementView.NAME);
            }
        });
        managementButton.setStyleName("link");
        layout.addComponent(managementButton);

        setContent(layout);
    }

    private boolean isOnPage(String viewName) {

        String currentPage = UI.getCurrent().getPage().getWindowName();
        if (currentPage.equals(viewName)) {
            return false;
        }
        return true;
    }

    private boolean authenticationRequired(String viewName) {

//        String currentPage = UI.getCurrent().getEmbedId();
//        User user = ((QoanUI) UI.getCurrent()).getUser();

        // for the time being stop this so
        // can't be bothered
//        if (ManagementView.NAME.equals(viewName)
//                || WorkspaceView.NAME.equals(viewName)
//                && user == null) {
//            return true;
//        }

        return false;
    }
}
