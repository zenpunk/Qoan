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
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import pl.pdfviewer.PdfViewer;
import qube.qai.services.implementation.SearchResult;

import java.io.File;

/**
 * Created by rainbird on 7/7/17.
 */
public class PdfFileDecorator extends Panel implements Decorator {

    private Image iconImage;

    public PdfFileDecorator() {
        iconImage = new Image("NGL-Viewer",
                new ClassResource("qube/qoan/gui/images/helix.jog"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {
        File pdfFile = new File("/home/rainbird/projects/work/docs/powerpoint/Qoan.pdf");
        PdfViewer pdfViewer = new PdfViewer(pdfFile);
//                pdfViewer.setHeight(400	,Unit.PIXELS);
//                pdfViewer.setWidth(800,Unit.PIXELS);
//                pdfViewer.setBackAngleButtonCaption(VaadinIcons.ROTATE_LEFT.getHtml());
//                pdfViewer.setNextAngleButtonCaption(VaadinIcons.ROTATE_RIGHT.getHtml());
//                pdfViewer.setIncreaseButtonCaption(VaadinIcons.SEARCH_PLUS.getHtml());
//                pdfViewer.setDecreaseButtonCaption(VaadinIcons.SEARCH_MINUS.getHtml());
//                pdfViewer.setPreviousPageCaption(VaadinIcons.ANGLE_LEFT.getHtml()+" Back");
//                pdfViewer.setNextPageCaption("Next "+VaadinIcons.ANGLE_RIGHT.getHtml());
        pdfViewer.setWidth("800px");
    }

    @Override
    public void addDecorator(String name, Decorator decorator) {

    }

    @Override
    public void decorateAll(SearchResult searchResult) {

    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }
}
