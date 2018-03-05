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
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import pl.pdfviewer.PdfViewer;

import java.io.File;

/**
 * Created by rainbird on 10/29/15.
 */
public class StartView extends QoanView {

    public static String NAME = "";

    private static String introduction = "<p align=justify><b>Artificial Neural-Networks</b> and <b>Graph</b> theories are commonly used tools in field " +
            "of <b>Artificial Intelligence</b>.</p> " +
            "<p align=justify><b>Qoan.org</b> is an open-source distributed artificial intelligence framework which aims to bring those tools " +
            "together in an user-friendly environment, helping visualize the data as well as organizing it. With this framework, we wish to integrate " +
            "openly accessible data sources, like Wikipedia, Wiktionary, DBpedia projects, as well as many different internet gene and molecular databases " +
            "for training specialized neural-networks to be employed in research, analysis and prognosis tasks.</p>";

    private static String loremIpsum =
            "<p align=justify><b><u>Koan:</u></b> a paradox to be meditated upon that is used to train " +
                    "Zen Buddhist monks to abandon ultimate dependence on reason and to " +
                    "force them into gaining sudden intuitive enlightenment" +
                    "<br>" +
                    "<b>Merriam-Webster Online Dictionary</b></p>" +
                    "<p align=justify>In order to achieve <i>artificial intelligence</i>, we must first be able to define <i>intelligence</i>.<br>" +
                    "<b>Anonymous common sense</b></p>" +
                    "<p><i>\"Networks, networks everywhere,<br>" +
                    "and when combined, more than just the sum of its parts\".</i><br>" +
                    "<b>Mugat Gurkowsky</b></p><br>" +
                    "<p>And of course,<br>" +
                    "<i>the proof of the pudding is in the eating.</i></p>";

    public StartView() {
        this.viewTitle = "Welcome to Qoan.org";
    }

    protected void initialize() {

        HorizontalSplitPanel panel = new HorizontalSplitPanel();

        ClassResource resource = new ClassResource("gui/images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        panel.setFirstComponent(image);
        panel.setSplitPosition(15, Unit.PERCENTAGE);

        VerticalLayout textLayout = new VerticalLayout();
        textLayout.setWidth("80%");

        Label introductionLabel = new Label(introduction, ContentMode.HTML);
        introductionLabel.setWidth("100%");
        textLayout.addComponent(introductionLabel);

        HorizontalLayout infoLine = new HorizontalLayout();
        Label moreInfolabel = new Label("For more information pelase refer to ");
        infoLine.addComponent(moreInfolabel);

        Button manifestoHtmlButton = new Button("Qoan Manifesto");
        manifestoHtmlButton.setStyleName("link");
        manifestoHtmlButton.addClickListener(event -> onOpenHtmlClicked());
        infoLine.addComponent(manifestoHtmlButton);

        Label orLabel = new Label("or as ");
        infoLine.addComponent(orLabel);

        Button manifestoPdfButton = new Button("Pdf-file");
        manifestoPdfButton.setStyleName("link");
        manifestoPdfButton.addClickListener(event -> onOpenPdfClicked());

        infoLine.addComponent(manifestoPdfButton);
        textLayout.addComponent(infoLine);

        Label loremIpsumLabel = new Label(StartView.loremIpsum, ContentMode.HTML);
        loremIpsumLabel.setWidth("100%");
        textLayout.addComponent(loremIpsumLabel);

        panel.addComponent(textLayout);
        addComponent(panel);
    }

    public void onOpenHtmlClicked() {

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        String filename = basepath + "/VAADIN/tmp/kickoff.xhtml";
        FileResource resource = new FileResource(new File(filename));

        BrowserFrame embeddedHtml = new BrowserFrame("", resource);
        embeddedHtml.setWidth("100%");
        embeddedHtml.setHeight("100%");

        Window window = new Window("Qoan Manifesto");
        window.setContent(embeddedHtml);
        window.setWidth("1000px");
        window.setHeight("800px");
        UI.getCurrent().addWindow(window);
    }

    public void onOpenPdfClicked() {

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        String filename = basepath + "/VAADIN/tmp/kickoff.pdf";
        File file = new File(filename);

        if (!file.exists()) {
            Notification.show("Error reading file: '" + file.getName() + "' does not exist!");
        }

        PdfViewer pdfViewer = new PdfViewer(file);
        pdfViewer.setWidth("100%");
        pdfViewer.setHeight("100%");

        Window window = new Window("Qoan Manifesto");
        window.setContent(pdfViewer);
        window.setWidth("1000px");
        window.setHeight("800px");
        UI.getCurrent().addWindow(window);
    }
}
