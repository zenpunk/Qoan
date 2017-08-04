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

package qube.qoan.gui.components.common.decorators;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.ResourceData;
import qube.qai.services.implementation.SearchResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;

/**
 * Created by rainbird on 7/7/17.
 */
@JavaScript({"pdf.js", "pdf.worker.js"})
public class PdfFileDecorator extends Panel implements Decorator {

    @Inject
    @Named("PdfFileResources")
    private QaiDataProvider<ResourceData> dataProvider;

    private Image iconImage;

    private String scriptToTemplate = "" +
            "// URL of PDF document\n" +
            //"var url = \"/WEB-INF/tmp/$s\";" +
            "var url = 'file:/home/rainbird/projects/work/docs/powerpoint/Qoan.pdf'" +
            "// Asynchronous download PDF\n" +
            "PDFJS.getDocument(url)\n" +
            "  .then(function(pdf) {\n" +
            "    return pdf.getPage(1);\n" +
            "  })\n" +
            "  .then(function(page) {\n" +
            "    // Set scale (zoom) level\n" +
            "    var scale = 1.5;\n" +
            "\n" +
            "    // Get viewport (dimensions)\n" +
            "    var viewport = page.getViewport(scale);\n" +
            "\n" +
            "    // Get canvas#the-canvas\n" +
            "    var canvas = document.getElementById('the-canvas');\n" +
            "\n" +
            "    // Fetch canvas' 2d context\n" +
            "    var context = canvas.getContext('2d');\n" +
            "\n" +
            "    // Set dimensions to Canvas\n" +
            "    canvas.height = viewport.height;\n" +
            "    canvas.width = viewport.width;\n" +
            "\n" +
            "    // Prepare object needed by render method\n" +
            "    var renderContext = {\n" +
            "      canvasContext: context,\n" +
            "      viewport: viewport\n" +
            "    };\n" +
            "\n" +
            "    // Render PDF page\n" +
            "    page.render(renderContext);\n" +
            "  });";

    public PdfFileDecorator() {
        iconImage = new Image("Pdf-Viewer",
                new ClassResource("gui/images/file.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        //File pdfFile = new File("/home/rainbird/projects/work/docs/powerpoint/Qoan.pdf");
        ResourceData data = dataProvider.brokerSearchResult(toDecorate);
        if (data == null) {
            Notification.show("No related data to '" + toDecorate.getTitle() + "' found, returning");
            return;
        }

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        String filename = basepath + "/WEB-INF/tmp/" + data.getName();
        File file = new File(filename);
        try {
            data.writeDataToFile(file);
        } catch (IOException e) {
            Notification.show("Error while writing file: '" + file.getName() + "'");
            return;
        }

        String iframe = "<canvas id=\"the-canvas\" width='800px' height='600px'></canvas>\n";
        Label iframeLabel = new Label(String.format(iframe, toDecorate.getTitle()));
        iframeLabel.setContentMode(ContentMode.HTML);
        setContent(iframeLabel);
        //String toExecute = "";
        String toRun = String.format(scriptToTemplate, filename);
        com.vaadin.ui.JavaScript.getCurrent().execute(toRun);

    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }
}
