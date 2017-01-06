package qube.qoan.gui.components.workspace;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import qube.qoan.gui.components.workspace.search.SearchMenu;

/**
 * Created by rainbird on 11/2/15.
 */
public class WorkSpace extends Panel {

    private HorizontalLayout layout;

    private TabSheet workspaceTabs;

    private SearchMenu searchMenu;

    public WorkSpace(SearchMenu searchMenu) {
        super();

        this.searchMenu = searchMenu;

        // do the initialization
        initialize();
    }

    private void initialize() {

        // as always begin with initializing the layout
        layout = new HorizontalLayout();

        workspaceTabs = new TabSheet();

        WorkspacePanel panel = new WorkspacePanel("Workspace 1");
        workspaceTabs.addTab(panel).setCaption("Workspace 1");
        DropHandler dropHandler = searchMenu.createDropHandler(panel.getBaseLayout());
        panel.setDropHandler(dropHandler);

        layout.addComponent(workspaceTabs);

        setContent(layout);
    }

    /**
     * adds a new tab to the view... mainly for the button below
     */
    public void addNewTab() {
        int count = workspaceTabs.getComponentCount() + 1;
        String title = "Workspace " + count;
        WorkspacePanel panel = new WorkspacePanel(title);
        DropHandler dropHandler = searchMenu.createDropHandler(panel.getBaseLayout());
        panel.setDropHandler(dropHandler);
        workspaceTabs.addTab(panel).setCaption(title);
    }

//    public void addComponentToDisplay(Component component) {
//        layout.addComponent(component);
//    }
}
