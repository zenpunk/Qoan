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

package qube.qoan.gui.components.common.tags;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.decorators.ImageDecorator;
import qube.qoan.gui.components.common.decorators.MolecularViewerDecorator;
import qube.qoan.gui.components.common.decorators.PdfFileDecorator;

/**
 * Created by rainbird on 7/7/17.
 */
public class ResourceTag extends BaseTag {

    public ResourceTag(SearchResult searchResult) {
        super(searchResult);
        iconImage = new Image("Resources",
                new ClassResource("qube/qoan/gui/images/readings.png"));
        decorators.put("Image Resource", new ImageDecorator());
        decorators.put("Pdf-Resource", new PdfFileDecorator());
        decorators.put("Molecular Viewer", new MolecularViewerDecorator());
    }

    /**
     *
     */
    @Override
    public void initialize() {

    }
}
