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

import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import pl.pdfviewer.PdfViewer;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.ResourceData;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.workspace.resource.DataStreamSource;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;

/**
 * Created by rainbird on 7/7/17.
 */
public class PdfFileDecorator extends Panel implements Decorator {

    @Inject
    @Named("PdfFileResources")
    private QaiDataProvider<ResourceData> dataProvider;

    private Image iconImage;

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

        PdfViewer pdfViewer = null;
        try {
            String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
            File file = new File(basepath + "/WEB-INF/tmp/" + data.getName());
            data.writeDataToFile(file);
            FileResource resource = new FileResource(file);
            pdfViewer = new PdfViewer(resource);
            pdfViewer.setHeight(400, Unit.PIXELS);
            pdfViewer.setWidth(800, Unit.PIXELS);
//                pdfViewer.setBackAngleButtonCaption(VaadinIcons.ROTATE_LEFT.getHtml());
//                pdfViewer.setNextAngleButtonCaption(VaadinIcons.ROTATE_RIGHT.getHtml());
//                pdfViewer.setIncreaseButtonCaption(VaadinIcons.SEARCH_PLUS.getHtml());
//                pdfViewer.setDecreaseButtonCaption(VaadinIcons.SEARCH_MINUS.getHtml());
//                pdfViewer.setPreviousPageCaption(VaadinIcons.ANGLE_LEFT.getHtml()+" Back");
//                pdfViewer.setNextPageCaption("Next "+VaadinIcons.ANGLE_RIGHT.getHtml());
//                pdfViewer.setWidth("800px");
        } catch (IOException e) {
            Notification.show("Error while reading file-data" + e.getMessage());
            return;
        }

        setContent(pdfViewer);
    }

    class DummyPdfViewer extends PdfViewer {

        public DummyPdfViewer(ResourceData data, String title) {
            super();
            StreamResource resource = new StreamResource(new DataStreamSource(data), title);
            setResource("resourceFile", resource);
        }
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }
}
