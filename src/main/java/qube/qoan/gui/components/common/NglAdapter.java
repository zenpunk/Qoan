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

package qube.qoan.gui.components.common;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Created by rainbird on 6/21/17.
 * This is supposed to add ngl-viewer in the application as a
 * Vaadin addon. should this work well enough, later i should consider
 * to make it in its own library, so that it can be released as a real Vaadin
 * addon too. for that is for later...
 */
@JavaScript({"ngl_viewer_adapter.js", "ngl.embedded.min.js"})
public class NglAdapter extends AbstractJavaScriptComponent implements Component.Listener {

    private String uuid;

    private String basic = "<div id='viewport' style='width:800px; height:600px;'></div>";

    public NglAdapter() {

        getState().xhtml = basic;

        UI.getCurrent().addListener(this);
    }

    public NglAdapter(String xhtml) {
        getState().xhtml = xhtml;
    }

    @Override
    public void componentEvent(Event event) {
        // Display source component and event class names
        //String eventString = "Event from " + event.getSource().getClass().getName() + ": " + event.getClass().getName();
        //Notification.show(eventString);
    }

    public void onClick() {
        Notification.show("OnCLick has been called on client-side");
    }

    /**
     *
     */
    @Override
    protected NglAdapterState getState() {
        return (NglAdapterState) super.getState();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
