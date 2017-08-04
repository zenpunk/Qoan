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
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.ResourceData;
import qube.qai.services.implementation.SearchResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by rainbird on 7/8/17.
 */
public class ImageDecorator extends Panel implements Decorator {

    private Image iconImage;

    @Inject
    @Named("WikiResources_en")
    private QaiDataProvider<ResourceData> dataProvider;

    public ImageDecorator() {
        iconImage = new Image("Image Viewer",
                new ClassResource("gui/images/image.png"));
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        ResourceData data = dataProvider.brokerSearchResult(toDecorate);
        if (data == null) {
            Notification.show("Corresponding data to: '" + toDecorate.getUuid() + "' could not be found");
            return;
        }

        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                InputStream stream = new ByteArrayInputStream(data.getBinaryData());
                return stream;
            }
        };

        StreamResource resource = new StreamResource(streamSource, toDecorate.getTitle());
        Image image = new Image(toDecorate.getTitle(), resource);

        setContent(image);
    }


}
