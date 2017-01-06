package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.pekka.resizablecsslayout.ResizableCssLayout;
import com.vaadin.ui.*;
import qube.qoan.gui.components.common.QoanHeader;
import qube.qoan.gui.components.workspace.procedure.ProcedureMenu;
import qube.qoan.gui.components.workspace.search.SearchMenu;
import qube.qoan.gui.components.workspace.WorkSpace;
import qube.qoan.gui.components.workspace.finance.FinanceMenu;

import java.lang.reflect.Method;

/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends VerticalLayout implements View {

    private boolean initialized = false;

    private boolean debug = true;

    public static String NAME = "workspace";

    private SearchMenu searchMenu;
    private FinanceMenu financeMenu;
    private ProcedureMenu procedureMenu;
    private WorkSpace workspace;

    private HorizontalSplitPanel splitPanel;
    private Component currentComponent;

    //@Override
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

        QoanHeader header = new QoanHeader();
        header.setWidth("100%");
        addComponent(header);

        splitPanel = new HorizontalSplitPanel();
        // create and add a dummy-component as place-filler
        currentComponent = new Panel();
        splitPanel.setFirstComponent(currentComponent);
        splitPanel.getFirstComponent().setVisible(true);

        // begin adding the first component
        // search-menu has to have a reference to the workspace in order to be able to add components to it
        searchMenu = new SearchMenu();
        searchMenu.setSizeUndefined();

        financeMenu = new FinanceMenu();
        financeMenu.setSizeUndefined();

        procedureMenu = new ProcedureMenu();
        procedureMenu.setSizeUndefined();

        workspace = new WorkSpace(searchMenu);
        splitPanel.setSecondComponent(workspace);

        addComponent(splitPanel);

        HorizontalLayout lowerLayout = new HorizontalLayout();

        try {
            // add a new tab to workspace
            Button addTabButton = new Button("Add New Tab");
            addTabButton.setStyleName("link");
            Method onAddTab = this.getClass().getMethod("onAddTab", Button.ClickEvent.class);
            addTabButton.addListener(Button.ClickEvent.class, this, onAddTab);
            lowerLayout.addComponent(addTabButton);

            // open the search menu
            Button showSearchMenuButton = new Button("Show Search Menu");
            showSearchMenuButton.setStyleName("link");
            Method onShowSearch = this.getClass().getMethod("onShowSearch", Button.ClickEvent.class);
            showSearchMenuButton.addListener(Button.ClickEvent.class, this, onShowSearch);
            lowerLayout.addComponent(showSearchMenuButton);

            // open the procedure menu
            Button showProcedureMenuButton = new Button("Show Procedure Menu");
            showProcedureMenuButton.setStyleName("link");
            Method onShowProcedure = this.getClass().getMethod("onShowProcedure", Button.ClickEvent.class);
            showProcedureMenuButton.addListener(Button.ClickEvent.class, this, onShowProcedure);
            lowerLayout.addComponent(showProcedureMenuButton);

            // open the finance-menu
            Button showFinanceMenuButton = new Button("Show Finance Menu");
            showFinanceMenuButton.setStyleName("link");
            Method onShowFinance = this.getClass().getMethod("onShowFinance", Button.ClickEvent.class);
            showFinanceMenuButton.addListener(Button.ClickEvent.class, this, onShowFinance);
            lowerLayout.addComponent(showFinanceMenuButton);

        } catch (NoSuchMethodException e) {
            Notification.show("NoSuchMethodException: " + e.getMessage());
        }

        addComponent(lowerLayout);
    }

    public void onShowProcedure(Button.ClickEvent event) {
        splitPanel.removeComponent(currentComponent);
        currentComponent = procedureMenu;
        currentComponent.setSizeFull();
        splitPanel.setFirstComponent(procedureMenu);
        if (debug) {
            Notification.show("Show process-menu");
        }
    }

    /**
     * listener method for showing market-menu
     * @param event
     */
    public void onShowFinance(Button.ClickEvent event) {
        splitPanel.removeComponent(currentComponent);
        currentComponent = financeMenu;
        currentComponent.setSizeFull();
        splitPanel.setFirstComponent(financeMenu);
        if (debug) {
            Notification.show("Show finance-menu");
        }
    }

    /**
     * listener method for showing search-menu
     * @param event
     */
    public void onShowSearch(Button.ClickEvent event) {
        splitPanel.removeComponent(currentComponent);
        currentComponent = searchMenu;
        currentComponent.setSizeFull();
        splitPanel.setFirstComponent(searchMenu);
        if (debug) {
            Notification.show("Show search menu");
        }
    }

    /**
     * listener method for adding a new tab to workspace
     * @param event
     */
    public void onAddTab(Button.ClickEvent event) {
        workspace.addNewTab();
        if (debug) {
            Notification.show("Add-tab button was clicked");
        }
    }
}
