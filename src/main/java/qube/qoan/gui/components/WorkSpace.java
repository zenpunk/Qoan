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
        setHeight("550");

        layout = new HorizontalLayout();

        workspaceTabs = new TabSheet();
        workspaceTabs.setHeight("100%");
        workspaceTabs.setWidth("100%");

        WorkspacePanel panel = new WorkspacePanel("Workspace 1");
        DragAndDropWrapper wrapper = new DragAndDropWrapper(panel);
        wrapper.setDropHandler(searchMenu.createDropHandler());
        workspaceTabs.addTab(wrapper).setCaption("Workspace 1");

//        WorkspacePanel panel2 = new WorkspacePanel("Workspace 2", searchMenu.createDropHandler());
//        workspaceTabs.addTab(panel2).setCaption("Workspace 2");

        layout.addComponent(workspaceTabs);

        // drag-n-drop things- more precisely a drop-handler
//        dndWrapper = new DragAndDropWrapper(workspaceTabs);


        // some dummy label do fill in the display
//        Label label = new Label("Display something as WorkSpace");
//        layout.addComponent(label);

        setContent(layout);
    }

//    /**
//     * exposes the Drag-n-Drop wrapper in order that events can be added as required
//     * @param handler
//     */
//    public void setDropHandler(DropHandler handler) {
////        dndWrapper.setDropHandler(handler);
//    }

    public void addNewTab() {
        int count = workspaceTabs.getComponentCount() + 1;
//        String title = "Workspace " + count;
//        WorkspacePanel workspacePanel = new WorkspacePanel(title);
//        workspaceTabs.addTab(workspacePanel).setCaption(title);
    }

    public void addComponentToDisplay(Component component) {
        layout.addComponent(component);
    }
}
