package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.pekka.resizablecsslayout.ResizableCssLayout;
import com.vaadin.pekka.resizablecsslayout.ResizableCssLayout;
import com.vaadin.ui.*;
import qube.qoan.gui.components.QoanHeader;
import qube.qoan.gui.components.workspace.procedure.ProcedureMenu;
import qube.qoan.gui.components.workspace.search.SearchMenu;
import qube.qoan.gui.components.workspace.WorkSpace;
import qube.qoan.gui.components.workspace.finance.FinanceMenu;

/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends VerticalLayout implements View {

    private boolean initialized = false;

    public static String NAME = "workspace";

    private boolean searchMenuVisible;
    private boolean financeMenuVisible;
    private boolean procedureVisible;

    private SearchMenu searchMenu;
    private FinanceMenu financeMenu;
    private ProcedureMenu procedureMenu;
    private WorkSpace workspace;

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

        // there is no real setting to this, just depends on what you are doing
        // and if the things are in the way really
        searchMenuVisible = true;
        financeMenuVisible = false;
        procedureVisible = false;

        QoanHeader header = new QoanHeader();
        header.setWidth("100%");
        addComponent(header);

        HorizontalLayout innerLayout = new HorizontalLayout();
        //AbsoluteLayout innerLayout = new AbsoluteLayout();
        //innerLayout.setSizeFull();

        // begin adding the first component
        // search-menu has to have a reference to the workspace in order to be able to add components to it
        searchMenu = new SearchMenu();
        searchMenu.setVisible(searchMenuVisible);
//        ResizableCssLayout searchMenuWrapper = new ResizableCssLayout();
//        searchMenuWrapper.addComponent(searchMenu);
//        searchMenuWrapper.setCaption("Drag to resize panel");
//        searchMenuWrapper.setWidth("200px");
//        searchMenuWrapper.setHeight("500px");
//        searchMenuWrapper.setResizable(true);
        innerLayout.addComponent(searchMenu); // , "top:10px; left:10px;"

        financeMenu = new FinanceMenu();
        financeMenu.setVisible(financeMenuVisible);
//        ResizableCssLayout financeMenuWrapper = new ResizableCssLayout();
//        financeMenuWrapper.addComponent(financeMenu);
//        financeMenuWrapper.setWidth("200px");
//        financeMenuWrapper.setHeight("500px");
//        financeMenuWrapper.setResizable(true);
//        financeMenuWrapper.setAutoAcceptResize(true);
//        financeMenuWrapper.setSidesResizable();
        innerLayout.addComponent(financeMenu); // , "top: 0px; left: 200px;"

        procedureMenu = new ProcedureMenu();
        procedureMenu.setVisible(procedureVisible);
//        ResizableCssLayout procedureMenuWrapper = new ResizableCssLayout();
//        procedureMenuWrapper.addComponent(procedureMenu);
//        procedureMenuWrapper.setWidth("20%");
//        procedureMenuWrapper.setHeight("100%");
//        procedureMenuWrapper.setResizable(true);
//        procedureMenuWrapper.setAutoAcceptResize(true);
//        procedureMenuWrapper.setSidesResizable();
        innerLayout.addComponent(procedureMenu); // , "top: 0px; left: 400px;"

        workspace = new WorkSpace(searchMenu);
//        ResizableCssLayout workspaceWrapper = new ResizableCssLayout();
//        workspaceWrapper.addComponent(workspace);
//        workspaceWrapper.setCaption("Drag to resize panel");
//        workspaceWrapper.setWidth("800px");
//        workspaceWrapper.setHeight("500px");
//        workspaceWrapper.setResizable(true);
        innerLayout.addComponent(workspace); // , "top:10px; left:210px;"

        addComponent(innerLayout);

        HorizontalLayout lowerLayout = new HorizontalLayout();

        Button showSearchMenuButton = new Button("Show SearchMenu");
//        showSearchMenuButton.setStyleName("link");
        showSearchMenuButton.addListener(Button.ClickEvent.class, this, "onShowSearch");
        lowerLayout.addComponent(showSearchMenuButton);

        Button addTabButton = new Button("Add New Tab");
//        addTabButton.setStyleName("link");
        addTabButton.addListener(Button.ClickEvent.class, this, "onAddTab");
        lowerLayout.addComponent(addTabButton);

        Button showStockMenuButton = new Button("Show Finance Menu");
//        showStockMenuButton.setStyleName("link");
        showStockMenuButton.addListener(Button.ClickEvent.class, this, "onShowFinance");
        lowerLayout.addComponent(showStockMenuButton);

        Button showProcedureMenuButton = new Button("Show Procedure Menu");
//        showProcedureMenuButton.setStyleName("link");
        showProcedureMenuButton.addListener(Button.ClickEvent.class, this, "onShowProcedure");
        lowerLayout.addComponent(showProcedureMenuButton);

        addComponent(lowerLayout);
    }

    public void onShowProcedure(Button.ClickEvent event) {
        procedureVisible = !procedureVisible;
        procedureMenu.setVisible(procedureVisible);
        Notification.show("Show process-menu");
    }

    /**
     * listener method for showing market-menu
     * @param event
     */
    public void onShowFinance(Button.ClickEvent event) {
        financeMenuVisible = !financeMenuVisible;
        financeMenu.setVisible(financeMenuVisible);
        Notification.show("Show finance-menu");
    }

    /**
     * listener method for showing search-menu
     * @param event
     */
    public void onShowSearch(Button.ClickEvent event) {
        searchMenuVisible = !searchMenuVisible;
        searchMenu.setVisible(searchMenuVisible);
        Notification.show("Show search menu");
    }

    /**
     * listener method for adding a new tab to workspace
     * @param event
     */
    public void onAddTab(Button.ClickEvent event) {
        workspace.addNewTab();
        Notification.show("Add-tab button was clicked");
    }
}
