package qube.qoan.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 10/30/15.
 */
public class WorkspaceView extends VerticalLayout implements View {

    public static String NAME = "workdspace";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UI.getCurrent().getPage().setTitle("Qoan Workspace");
    }
}
