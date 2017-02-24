package qube.qoan.gui.views;

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 10/29/15.
 */
public class StartBaseView extends QoanBaseView {

    public static String NAME = "";

    private static String loremIpsum =
            "<p><b><i><u>Koan:</u></i></b> a paradox to be meditated upon that is used to train " +
                    "Zen Buddhist monks to abandon ultimate dependence on reason and to " +
                    "force them into gaining sudden intuitive enlightenment" +
                    "<br><b><i>Merriam-Webster Online Dictionary</i></b></p>";// +
    //"<p><i>Singularity is nigh!</i></p>";

//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        VerticalLayout layout = new VerticalLayout();
//        layout.setWidth("100%");
//
//        QoanHeader header = new QoanHeader();
//        layout.addComponent(header);
//
//        UI.getCurrent().getPage().setTitle("Welcome to Qoan");
//
//
//    }


    public StartBaseView() {
        this.viewTitle = "Welcome to Qoan";
    }

    protected void initialize() {

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("800px");

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setWidth("800px");
        ClassResource resource = new ClassResource("images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        firstRow.addComponent(image);

        Label loremIpsum = new Label(StartBaseView.loremIpsum, ContentMode.HTML);
        loremIpsum.setStyleName("justified", true);
        firstRow.addComponent(loremIpsum);
        contentLayout.addComponent(firstRow);

        addComponent(contentLayout);
        //addComponent(layout);
    }
}
