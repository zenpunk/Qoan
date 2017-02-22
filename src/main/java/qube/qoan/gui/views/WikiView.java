package qube.qoan.gui.views;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.UI;

/**
 * Created by rainbird on 1/10/16.
 */
public class WikiView extends BaseQoanView {

    public static String NAME = "qoanwiki";

    // for the moment, this need to be in a configuration even, as i have
    // only one wiki-server after all... later, much later...
    private String wikiUrl = "http://192.168.1.4:8081/wiki/en/Welcome_Page";

    public WikiView() {
        this.viewTitle = "Qoan Wiki";
    }


    /**
     * this is mainly for redirecting the view to QoanWiki which is
     * already running on the network- in way of documentation and etc.
     * @param
     */
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        UI.getCurrent().getPage().setTitle("Qoan Wiki");
//        QoanHeader header = new QoanHeader();
//        header.setWidth("100%");
//        addComponent(header);
//     }
    @Override
    protected void initialize() {

        int width = UI.getCurrent().getPage().getBrowserWindowWidth();
        int height = UI.getCurrent().getPage().getBrowserWindowHeight();
        float headerHeight = header.getHeight();

        BrowserFrame frame = new BrowserFrame(null, new ExternalResource(wikiUrl));
        frame.setWidth(width + "px");
        frame.setHeight((height-headerHeight) + "px");
        addComponent(frame);
    }
}
