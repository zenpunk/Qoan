package qube.qoan.gui.components;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 10/31/15.
 */
public class SearchMenu extends Panel {

    public SearchMenu() {
        super();

        // do the initialization
        initialize();
    }

    private void initialize() {
        // begin with setting the size
        setWidth("300");
        setHeight("550");

        VerticalLayout layout = new VerticalLayout();

        // some dummy label do fill in the display
        Label label = new Label("Display something as SearchMenu");
        layout.addComponent(label);

        setContent(layout);
    }
}
