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

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

/**
 * Created by rainbird on 10/29/15.
 */
public class StartView extends QoanView {

    public static String NAME = "";

    private static String introduction = "<p><b>Artificial Neural-Networks</b> and <b>Graph</b> theories are commonly used tools in field " +
            "of <b>Artificial Intelligence</b>.</p> " +
            "<p><b>Qoan.org</b> is an open-source distributed artificial intelligence framework which aims to bring those tools " +
            "together in an user-friendly environment, helping visualize the data as well as organizing it. With this framework, we wish to integrate " +
            "openly accessible data sources, like Wikipedia, Wiktionary, DBpedia projects, as well as many different internet gene and molecular databases " +
            "for research, analysis and prognosis tasks.</p>";

    private static String loremIpsum =
            "<p><b><u>Koan:&nbsp;</u></b> a paradox to be meditated upon that is used to train " +
                    "Zen Buddhist monks to abandon ultimate dependence on reason and to " +
                    "force them into gaining sudden intuitive enlightenment" +
                    "<br>" +
                    "<b><i>Merriam-Webster Online Dictionary</i></b></p>" +
                    "<p><i>In order to achieve <b>artificial intelligence</b>, we must first be able to define <b>intelligence</b>.<br>" +
                    "<b>Anonymous common sense</b></i></p>" +
                    "<p><i>\"Networks, networks everywhere,<br>" +
                    "and when combined, more than just the sum of its parts\".</i><br>" +
                    "<b><i>Mugat Gurkowsky</i></b></p><br>" +
                    "<p><i>And of course,<br>" +
                    "the proof of the pudding is in the eating.</i></p>";

    public StartView() {
        this.viewTitle = "Welcome to Qoan";
    }

    protected void initialize() {

        //VerticalLayout contentLayout = new VerticalLayout();
        //contentLayout.setWidth("80%");

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setWidth("100%");
        ClassResource resource = new ClassResource("gui/images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        image.setWidth("30%");
        firstRow.addComponent(image);

        Label introductionLabel = new Label(introduction, ContentMode.HTML);
        introductionLabel.setWidth("50%");
        firstRow.addComponent(introductionLabel);

        Label loremIpsumLabel = new Label(StartView.loremIpsum, ContentMode.HTML);
        loremIpsumLabel.setWidth("50%");
        //loremIpsum.setStyleName("paragraph", true);
        firstRow.addComponent(loremIpsumLabel);
        //contentLayout.addComponent(firstRow);

        addComponent(firstRow);
        //addComponent(layout);
    }
}
