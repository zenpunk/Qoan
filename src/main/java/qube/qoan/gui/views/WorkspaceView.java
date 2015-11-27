package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import qube.qoan.gui.components.workspace.finance.MarketMenu;
import qube.qoan.gui.components.QoanHeader;
import qube.qoan.gui.components.workspace.SearchMenu;
import qube.qoan.gui.components.workspace.WorkSpace;

/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends VerticalLayout implements View {

    private boolean initialized = false;

    public static String NAME = "workspace";

    private boolean searchMenuVisible;
    private boolean marketMenuVisible;

    private SearchMenu searchMenu;
    private MarketMenu marketMenu;
    private WorkSpace workSpace;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UI.getCurrent().getPage().setTitle("Qoan Workspace");

        if (!initialized) {
            initialize();
            initialized = true;
        }

    }

    /**
     * adds all of the components to the view
     */
    private void initialize() {
        searchMenuVisible = true;
        marketMenuVisible = true;

        Float windowWidth = UI.getCurrent().getWidth();
        Float windowHeight = UI.getCurrent().getHeight();

        QoanHeader header = new QoanHeader();
        header.setWidth("100%");
        addComponent(header);

        HorizontalLayout innerLayout = new HorizontalLayout();

        // begin adding the first component
        // search-menu has to have a reference to the workspace in order to be able to add components to it
        searchMenu = new SearchMenu();
        searchMenu.setWidth("30%");
        searchMenu.setHeight("100%");
        innerLayout.addComponent(searchMenu);

        marketMenu = new MarketMenu();
        marketMenu.setWidth("30%");
        marketMenu.setHeight("100%");
        innerLayout.addComponent(marketMenu);

        workSpace = new WorkSpace(searchMenu);
        workSpace.setWidth("80%");
        workSpace.setHeight("100%");
        innerLayout.addComponent(workSpace);

        addComponent(innerLayout);

        HorizontalLayout lowerLayout = new HorizontalLayout();

        Button showSearchMenuButton = new Button("Show SearchMenu");
        showSearchMenuButton.setStyleName("link");
        showSearchMenuButton.addListener(Button.ClickEvent.class, this, "onShowSearch");
        lowerLayout.addComponent(showSearchMenuButton);

        Button addTabButton = new Button("Add New Tab");
        addTabButton.setStyleName("link");
        addTabButton.addListener(Button.ClickEvent.class, this, "onAddTab");
        lowerLayout.addComponent(addTabButton);

        Button showStockMenuButton = new Button("Show Stock-Market Menu");
        showStockMenuButton.setStyleName("link");
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
