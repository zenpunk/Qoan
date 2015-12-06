package qube.qoan.gui.components.workspace;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * Created by rainbird on 11/14/15.
 */
public class WorkspacePanel extends Panel {

    private String title;

    private AbsoluteLayout layout;

    public DragAndDropWrapper layoutWrapper;

    public WorkspacePanel(String title) {
        this.title = title;

        initialize();
    }

    private void initialize() {

        layout = new AbsoluteLayout();
        layout.setWidth("1400px");
        layout.setHeight("500px");

        Label titleLabel = new Label(title);
        DragAndDropWrapper wrapper = new DragAndDropWrapper(titleLabel);
        wrapper.setSizeUndefined();
        wrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
        layout.addComponent(wrapper, "right: 50px; top: 50px;");

        layoutWrapper = new DragAndDropWrapper(layout);
        //layoutWrapper.setDropHandler(new MoveHandler());
        // and finally set the
        setContent(layoutWrapper);
    }

    public void setDropHandler(DropHandler dropHandler) {
        layoutWrapper.setDropHandler(dropHandler);
    }

    public AbsoluteLayout getBaseLayout() {
        return layout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
