package qube.qoan.gui.views;

import com.google.inject.Injector;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.common.QoanHeader;

/**
 * Created by rainbird on 2/22/17.
 */
public abstract class QoanView extends VerticalLayout implements View {

    private boolean debug = true;

    protected boolean initialized = false;

    protected String viewTitle = "Base Qoan Page";

    protected String viewName = "Base View";

    protected QoanHeader header;

    public QoanView() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        UI.getCurrent().getPage().setTitle(viewTitle);

        if (!initialized) {

            Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
            injector.injectMembers(this);

            header = new QoanHeader();
            header.setWidth("100%");
            addComponent(header);

            initialize();
            initialized = true;
        }
    }

    protected abstract void initialize();

}
