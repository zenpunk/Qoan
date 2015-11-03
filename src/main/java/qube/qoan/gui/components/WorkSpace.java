package qube.qoan.gui.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 11/2/15.
 */
public class WorkSpace extends Panel {

    public WorkSpace() {
        super();

        // do the initialization
        initialize();
    }

    private void initialize() {
        // begin with setting the size
        setWidth("1200");
        setHeight("550");

        VerticalLayout layout = new VerticalLayout();

        // some dummy label do fill in the display
        Label label = new Label("Display something as WorkSpace");
        layout.addComponent(label);

        setContent(layout);
    }
}
