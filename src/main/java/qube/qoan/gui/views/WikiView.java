/*
 * Copyright 2017 Qoan Software Association. All rights reserved.
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

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.UI;

/**
 * Created by rainbird on 1/10/16.
 */
public class WikiView extends QoanView {

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
     *
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
        frame.setHeight((height - headerHeight) + "px");
        addComponent(frame);
    }
}
