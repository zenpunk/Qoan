package qube.qoan.gui.components;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.*;

/**
 * Created by rainbird on 11/2/15.
 */
public class WorkSpace extends Panel {

    private HorizontalLayout layout;

    private DragAndDropWrapper dndWrapper;

    private TabSheet workspaceTabs;

    private SearchMenu searchMenu;

    public WorkSpace(SearchMenu searchMenu) {
        super();

        this.searchMenu = searchMenu;

        // do the initialization
        initialize();
    }

    private void initialize() {
        // begin with setting the size
        setWidth("1000");
        setHeight("500");

        layout = new HorizontalLayout();

        workspaceTabs = new TabSheet();
        workspaceTabs.setHeight("100%");
        workspaceTabs.setWidth("100%");

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

    public void addComponentToDisplay(Component component) {
        layout.addComponent(component);
    }
}
