package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import qube.qoan.gui.components.MarketMenu;
import qube.qoan.gui.components.QoanHeader;
import qube.qoan.gui.components.SearchMenu;
import qube.qoan.gui.components.WorkSpace;

/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends VerticalLayout implements View {

    public static String NAME = "workspace";

    private boolean searchMenuVisible;
    private boolean marketMenuVisible;

    private SearchMenu searchMenu;
    private MarketMenu marketMenu;
    private WorkSpace workSpace;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UI.getCurrent().getPage().setTitle("Qoan Workspace");

        searchMenuVisible = true;
        marketMenuVisible = true;

        QoanHeader header = new QoanHeader();
        addComponent(header);

        HorizontalLayout innerLayout = new HorizontalLayout();

        // begin adding the first component
        // search-menu has to have a reference to the workspace in order to be able to add components to it
        searchMenu = new SearchMenu();
        innerLayout.addComponent(searchMenu);

        marketMenu = new MarketMenu();
        innerLayout.addComponent(marketMenu);

        workSpace = new WorkSpace(searchMenu);
        innerLayout.addComponent(workSpace);

        addComponent(innerLayout);

        HorizontalLayout lowerLayout = new HorizontalLayout();

        Button showSearchMenuButton = new Button("Show SearchMenu");
        showSearchMenuButton.addListener(Button.ClickEvent.class, this, "onShowSearch");
        lowerLayout.addComponent(showSearchMenuButton);

        Button addTabButton = new Button("Add New Tab");
        addTabButton.addListener(Button.ClickEvent.class, this, "onAddTab");
        lowerLayout.addComponent(addTabButton);

        Button showStockMenuButton = new Button("Show Stock-Market Menu");
        showStockMenuButton.addListener(Button.ClickEvent.class, this, "onShowMarket");
        lowerLayout.addComponent(showStockMenuButton);

        addComponent(lowerLayout);
    }

    /**
     * listener method for showing market-menu
     * @param event
     */
    public void onShowMarket(Button.ClickEvent event) {
        if (marketMenuVisible) {
            marketMenuVisible = false;
        } else {
            marketMenuVisible = true;
        }
        marketMenu.setVisible(marketMenuVisible);
        Notification.show("Show market menu button was clicked");
    }

    /**
     * listener method for showing search-menu
     * @param event
     */
    public void onShowSearch(Button.ClickEvent event) {
        if (searchMenuVisible) {
            searchMenuVisible = false;
        } else {
            searchMenuVisible = true;
        }
        searchMenu.setVisible(searchMenuVisible);
        Notification.show("Show search menu button was clicked");
    }

    /**
     * listener method for adding a new tab to workspace
     * @param event
     */
    public void onAddTab(Button.ClickEvent event) {
        workSpace.addNewTab();
        Notification.show("Add-tab button was clicked");
    }
}
