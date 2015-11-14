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

//    private DragAndDropWrapper dndWrapper;

    private DropHandler dropHandler;

    public WorkspacePanel(String title) {
        this.title = title;

        initialize();
    }

    private void initialize() {

        layout = new AbsoluteLayout();
        layout.setWidth("1000px");
        layout.setHeight("550px");


//        dndWrapper = new DragAndDropWrapper(layout);
//
//       dndWrapper.setDropHandler(dropHandler);

        Label titleLabel = new Label(title);
        DragAndDropWrapper wrapper = new DragAndDropWrapper(titleLabel);
        wrapper.setSizeUndefined();
        wrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
        layout.addComponent(wrapper, "left: 50px; top: 50px;");

        DragAndDropWrapper layoutWrapper = new DragAndDropWrapper(layout);
        layoutWrapper.setDropHandler(new MoveHandler());
        // and finally set the
        setContent(layoutWrapper);
    }

    // Handles drops both on an AbsoluteLayout and
    // on components contained within it
    class MoveHandler implements DropHandler {
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }

        public void drop(DragAndDropEvent event) {
            DragAndDropWrapper.WrapperTransferable t =
                    (DragAndDropWrapper.WrapperTransferable) event.getTransferable();
            DragAndDropWrapper.WrapperTargetDetails details =
                    (DragAndDropWrapper.WrapperTargetDetails) event.getTargetDetails();

            // Calculate the drag coordinate difference
            int xChange = details.getMouseEvent().getClientX()
                    - t.getMouseDownEvent().getClientX();
            int yChange = details.getMouseEvent().getClientY()
                    - t.getMouseDownEvent().getClientY();

            // Move the component in the absolute layout
            AbsoluteLayout.ComponentPosition pos = layout.getPosition(t.getSourceComponent());
            pos.setLeftValue(pos.getLeftValue() + xChange);
            pos.setTopValue(pos.getTopValue() + yChange);
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
