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
import qube.qai.procedure.utils.SelectionProcedure;
import qube.qai.services.implementation.SearchResult;

public class SelectionDecorator extends BaseDecorator {

    private Image iconImage;

    private SelectionProcedure selection;

    private String name;

    public SelectionDecorator(String name, SelectionProcedure selection) {
        this.name = "Selection for " + name;
        this.selection = selection;
        this.iconImage = new Image(name,
                new ClassResource("gui/images/selection-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        String mimeType = selection.getValueTo().getMimeType();



    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public String getName() {
        return name;
    }
}
