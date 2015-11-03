package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import qube.qoan.gui.components.SearchMenu;
import qube.qoan.gui.components.WorkSpace;

/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends VerticalLayout implements View {

    public static String NAME = "workspace";

    private boolean searchVisible;
    private boolean workspaceVisible;

    private SearchMenu searchMenu;
    private WorkSpace workSpace;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UI.getCurrent().getPage().setTitle("Qoan Workspace");

        searchVisible = true;
        workspaceVisible = true;

        HorizontalLayout innerLayout = new HorizontalLayout();

        // begin adding the first component
        searchMenu = new SearchMenu();
        innerLayout.addComponent(searchMenu);

        workSpace = new WorkSpace();
        innerLayout.addComponent(workSpace);

        addComponent(innerLayout);

        HorizontalLayout lowerLayout = new HorizontalLayout();

        Button showSearchMenuButton = new Button("Show SearchMenu");
        showSearchMenuButton.addListener(Button.ClickEvent.class, this, "onShowSearch");
        lowerLayout.addComponent(showSearchMenuButton);

        Button showAddTabButton = new Button("Add New Tab");
        showAddTabButton.addListener(Button.ClickEvent.class, this, "onAddTab");
        lowerLayout.addComponent(showAddTabButton);

        addComponent(lowerLayout);
    }

    public void onShowSearch(Button.ClickEvent event) {
        if (searchVisible) {
            searchVisible = false;
        } else {
            searchVisible = true;
        }
        searchMenu.setVisible(searchVisible);
        Notification.show("Search button was clicked");
    }

    public void onAddTab(Button.ClickEvent event) {
        if (workspaceVisible) {
            workspaceVisible = false;
        } else {
            workspaceVisible = true;
        }
        workSpace.setVisible(workspaceVisible);
        Notification.show("Tab Add button was clicked");
    }
}
