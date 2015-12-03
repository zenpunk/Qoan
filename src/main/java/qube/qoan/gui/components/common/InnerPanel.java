package qube.qoan.gui.components.common;

import com.vaadin.ui.*;

/**
 * Created by rainbird on 12/3/15.
 */
public class InnerPanel extends Panel {

    /**
     * this class is a substitute to Vaadin's native
     * window, in order to remedy some of its drawbacks
     * InnerPanels can be opened, closed and dragged around
     * on the Workspace
     */
    public InnerPanel(Component content) {

        super();

        initialize(content);
    }

    private void initialize(Component content) {

        // Begin with layout
        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout topLayout = new HorizontalLayout();
        Button closeButton = new Button("close");
        closeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setVisible(false);
            }
        });

        topLayout.addComponent(closeButton);
        topLayout.setComponentAlignment(closeButton, Alignment.TOP_LEFT);
        layout.addComponent(topLayout);

        // now add the given component
        layout.addComponent(content);

        setContent(layout);
    }
}
