package qube.qoan.authentication;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.gui.views.LoginBaseView;
import qube.qoan.gui.views.ManagementBaseView;
import qube.qoan.gui.views.WorkspaceBaseView;

/**
 * Created by rainbird on 12/25/15.
 */
public class SecureViewChangeListener implements ViewChangeListener {

    private Logger logger = LoggerFactory.getLogger("SecureViewChangeListener");

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        View nextView = event.getNewView();
        View currentView = event.getOldView();

        // check if the user clicked the same view
        if (nextView.equals(currentView)) {
            return false;
        }

        // check if user is already logged in
        if (nextView instanceof WorkspaceBaseView
                || nextView instanceof ManagementBaseView) {

            User user = ((QoanUI)UI.getCurrent()).getUser();
            if (user == null) {
                UI.getCurrent().getNavigator().navigateTo(LoginBaseView.NAME);
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        logger.info("View changed to: " + event.getNewView().toString());
    }
}
