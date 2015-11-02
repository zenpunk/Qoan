package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 10/30/15.
 * here we need absolute layout, because we want this part of the application to be
 * drag & drop
 */
public class WorkspaceView extends AbsoluteLayout implements View {

    public static String NAME = "workdspace";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UI.getCurrent().getPage().setTitle("Qoan Workspace");

        // begin adding the first component

    }
}
