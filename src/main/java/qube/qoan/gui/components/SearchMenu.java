package qube.qoan.gui.components;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qai.main.QaiModule;
import qube.qai.services.SearchServiceInterface;
import qube.qoan.services.QoanModule;
import qube.qoan.services.SearchService;

import javax.inject.Inject;

/**
 * Created by rainbird on 10/31/15.
 */
public class SearchMenu extends Panel {

//    @Inject @Named("Wiktionary_en")
//    private SearchServiceInterface searchService;

    public SearchMenu() {
        super();

        // @TODO initialization doesn't work due to project dependencies- skip for the time being
        // i think this is more or less what we will need to do in order to make the injector to work
//        Injector injector = Guice.createInjector(new QoanModule(), new QaiModule());
//        injector.injectMembers(this);
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
