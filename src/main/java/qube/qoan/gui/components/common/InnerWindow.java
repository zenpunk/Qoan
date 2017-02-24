package qube.qoan.gui.components.common;

import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by rainbird on 12/3/15.
 */
public class InnerWindow extends Window {

    /**
     * this class is a substitute to Vaadin's native
     * window, in order to remedy some of its drawbacks
     * InnerPanels can be opened, closed and dragged around
     * on the Workspace
     */
    public InnerWindow(String title, Component content) {

        super();

        initialize(title, content);
    }

    private void initialize(String title, Component content) {

        // Begin with layout
        //VerticalLayout layout = new VerticalLayout();

        AbsoluteLayout topLayout = new AbsoluteLayout();
        topLayout.setWidth("600px");
        topLayout.setHeight("400px");
        // add the title only if there is actually something there
        if (StringUtils.isNotBlank(title)) {
            Label titleLabel = new Label(title);
            titleLabel.setStyleName("bold");
            topLayout.addComponent(titleLabel, "top:10px; left:10px;");
        }

        Button closeButton = new Button("X");
        closeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setVisible(false);
            }
        });
        closeButton.setStyleName("link");
        topLayout.addComponent(closeButton, "top:0px; right:10px;");
        topLayout.setStyleName("hover");
        //layout.addComponent(topLayout);

        // now add the given component
        Panel contentPanel = new Panel(content);
        contentPanel.setWidth("380px");
        contentPanel.setHeight("370px");
        //layout.addComponent(content);
        topLayout.addComponent(content, "top:30; right:0px; bottom:1px; left:0px;");
        //layout.setMargin(true);
        //setContent(layout);
        setContent(topLayout);
    }

}
