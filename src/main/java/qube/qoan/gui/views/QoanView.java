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

package qube.qoan.gui.views;

import com.google.inject.Injector;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qoan.gui.components.common.QoanHeader;
import qube.qoan.services.QoanInjectorService;

/**
 * Created by rainbird on 2/22/17.
 */
public abstract class QoanView extends VerticalLayout implements View {

    protected Logger logger = LoggerFactory.getLogger("View-Messages");

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

            //Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
            Injector injector = QoanInjectorService.getInstance().getInjector();
            injector.injectMembers(this);

            header = new QoanHeader();
            header.setWidth("100%");
            addComponent(header);

            initialize();
            initialized = true;
        }
    }

    /**
     * this is the method which will be called to build up the whole
     * screen and elements which are to be used therein.
     */
    protected abstract void initialize();

    /**
     * use this message for logging info-messages
     *
     * @param message
     */
    protected void log(String message) {
        logger.info(message);
    }

    /**
     * use this message for logging error-messages
     *
     * @param message
     */
    protected void logError(String message, Exception exception) {
        logger.error(message, exception);
    }

    /**
     * use this message for logging debug-messages
     *
     * @param message
     */
    protected void logDebug(String message) {
        logger.debug(message);
    }
}
