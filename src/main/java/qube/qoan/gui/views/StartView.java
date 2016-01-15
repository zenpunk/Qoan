package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import qube.qoan.gui.components.common.QoanHeader;

/**
 * Created by rainbird on 10/29/15.
 */
public class StartView extends VerticalLayout implements View {

    public static String NAME = "";

    private static String loremIpsum =
            "<p><b>Koan:</b> a paradox to be meditated upon that is used to train " +
                    "Zen Buddhist monks to abandon ultimate dependence on reason and " +
                    "to force them into gaining sudden intuitive enlightenment." +
                    "<br><b>Merriam Webster Online Dictionary</b></p>";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");

        QoanHeader header = new QoanHeader();
        layout.addComponent(header);

        UI.getCurrent().getPage().setTitle("Welcome to Qoan");

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("800px");

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setWidth("800px");
        ClassResource resource = new ClassResource("images/kokoline.gif");
        Image image = new Image(null, resource);
        firstRow.addComponent(image);

        Label loremIpsum = new Label(StartView.loremIpsum, ContentMode.HTML);
        loremIpsum.setStyleName("justified", true);
        firstRow.addComponent(loremIpsum);
        contentLayout.addComponent(firstRow);

        layout.addComponent(contentLayout);
        addComponent(layout);
    }
}
