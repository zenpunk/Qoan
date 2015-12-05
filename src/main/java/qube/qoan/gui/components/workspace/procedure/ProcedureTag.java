package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.ui.*;
import qube.qai.procedure.Procedure;
import qube.qoan.gui.components.common.InnerPanel;
import qube.qoan.gui.components.common.MetricsPanel;

/**
 * Created by rainbird on 12/5/15.
 */
public class ProcedureTag extends Panel {

    private Procedure procedure;
    private Layout parentLayout;

    private int top = 100;
    private int left = 100;

    public ProcedureTag(Procedure procedure, Layout parentLayout) {
        this.procedure = procedure;
        this.parentLayout = parentLayout;

        initialize(procedure);
    }

    private void initialize(Procedure procedure) {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("200px");

        String name = null;
        if (procedure.hasExecuted()) {
            name = procedure.getName() + "[" + procedure.getUuid() + "]";
        } else {
            name = procedure.getName();
        }
        Label nameLabel = new Label(name);
        nameLabel.setStyleName("bold");
        layout.addComponent(nameLabel);

        Label descriptionLabel = new Label(procedure.getDescription());
        layout.addComponent(descriptionLabel);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button openButton = new Button("open");
        openButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String title = procedure.getName();
                MetricsPanel metricsPanel = new MetricsPanel("Parameters:", procedure.getArguments());

                InnerPanel window = new InnerPanel(title, metricsPanel);
//                window.setWidth("600px");
//                window.setHeight("400px");
                // if parent is an absolute layout, we need a position to add the thing as well
                if (parentLayout instanceof AbsoluteLayout) {
                    left = left + 5;
                    top = top + 5;
                    String positionString = "left: " + left + "px; top: " + top + "px;";
                    DragAndDropWrapper dndWrapper = new DragAndDropWrapper(window);
                    dndWrapper.setSizeUndefined();
                    dndWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
                    ((AbsoluteLayout)parentLayout).addComponent(dndWrapper, positionString);
                } else {
                    parentLayout.addComponent(window);
                }
            }
        });
        openButton.setStyleName("link");
        buttonLayout.addComponent(openButton);

        Button removeButton = new Button("remove");
        removeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // we are removing the parent- which is the dnd-wrapper
                Component parent = getParent();
                parentLayout.removeComponent(parent);
            }
        });
        removeButton.setStyleName("link");
        buttonLayout.addComponent(removeButton);
        layout.addComponent(buttonLayout);

        setContent(layout);
    }
}
