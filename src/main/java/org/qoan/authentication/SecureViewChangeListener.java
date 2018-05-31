/*
 * Copyright 2017 Qoan Wissenschaft & Software. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package org.qoan.authentication;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import org.qai.user.User;
import org.qoan.QoanUI;
import org.qoan.gui.views.LoginView;
import org.qoan.gui.views.ManagementView;
import org.qoan.gui.views.WorkspaceView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        if (nextView instanceof WorkspaceView || nextView instanceof ManagementView) {

            // @TODO this is not right- have to get the session information to Shiro somehow...
            //Subject subject = SecurityUtils.getSubject();
            //if (!subject.isAuthenticated()) {
            User user = ((QoanUI) UI.getCurrent()).getUser();
            if (user == null) {
                UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
                logger.info("Requested view: " + nextView.toString() + " redirected to LoginView");
                return false;
            }
        }

        return true;
    }

}
