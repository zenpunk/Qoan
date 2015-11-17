package qube.qoan.gui.components;

import com.vaadin.data.Item;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.ui.*;
import qube.qai.persistence.WikiArticle;

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
        layout.setWidth("1000px");
        layout.setHeight("550px");

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
