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

package org.qoan.gui.components.common.decorators;

import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import org.qai.persistence.QaiDataProvider;
import org.qai.persistence.ResourceData;
import org.qai.services.implementation.SearchResult;
import pl.pdfviewer.PdfViewer;

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

    private String name = "Pdf-Viewer";

    public PdfFileDecorator() {
        iconImage = new Image(name,
                new ClassResource("gui/images/pdf-file-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        ResourceData data = dataProvider.brokerSearchResult(toDecorate);
        if (data == null) {
            Notification.show("No related data to '" + toDecorate.getTitle() + "' not found, returning");
            return;
        }

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        String filename = basepath + "/VAADIN/tmp/" + data.getName();
        File file = new File(filename);
        try {
            data.writeDataToFile(file);
        } catch (IOException e) {
            Notification.show("Error writing file: '" + file.getName() + "'");
            return;
        }

        if (!file.exists()) {
            Notification.show("Error reading file: '" + file.getName() + "' does not exist!");
        }

        PdfViewer pdfViewer = new PdfViewer(file);
        pdfViewer.setSizeFull();
        /*
        setPreviousPageCaption(String htmlCaption)
        setNextPageCaption(String htmlCaption)
        setPageCaption(String htmlCaption)
        setToPageCaption(String htmlCaption)
        setIncreaseButtonCaption(String htmlCaption)
        setDecreaseButtonCaption(String htmlCaption)
        setNextAngleButtonCaption(String htmlCaption)
        setBackAngleButtonCaption(String htmlCaption)
        setPrintButtonCaption(String htmlCaption)
        setDownloadButtonCaption(String htmlCaption)
        setAngleButtonVisible(boolean visible)
        setDownloadBtnVisible(boolean visible)
        addPageChangeListener(PageChangeListener listener)
        removePageChangeListener(PageChangeListener listener)
        addAngleChangeListener(AngleChangeListener listener)
        removeAngleChangeListener(AngleChangeListener listener)
        addDownloadTiffListener(DownloadTiffListener listener)
        removeDownloadTiffListener(DownloadTiffListener listener)
         */
        setContent(pdfViewer);

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }
}
