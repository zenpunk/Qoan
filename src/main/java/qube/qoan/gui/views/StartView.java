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

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 10/29/15.
 */
public class StartView extends QoanView {

    public static String NAME = "";

    private static String loremIpsum =
            "<p><b><i><u>Koan:</u></i></b> a paradox to be meditated upon that is used to train " +
                    "Zen Buddhist monks to abandon ultimate dependence on reason and to " +
                    "force them into gaining sudden intuitive enlightenment" +
                    "<br><b><i>Merriam-Webster Online Dictionary</i></b></p>";// +

    public StartView() {
        this.viewTitle = "Welcome to Qoan";
    }

    protected void initialize() {

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setWidth("800px");

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setWidth("800px");
        ClassResource resource = new ClassResource("qube/qoan/gui/images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        firstRow.addComponent(image);

        Label loremIpsum = new Label(StartView.loremIpsum, ContentMode.HTML);
        loremIpsum.setStyleName("justified", true);
        firstRow.addComponent(loremIpsum);
        contentLayout.addComponent(firstRow);

        addComponent(contentLayout);
        //addComponent(layout);
    }
}
