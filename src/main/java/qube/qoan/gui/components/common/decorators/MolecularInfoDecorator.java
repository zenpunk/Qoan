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

package qube.qoan.gui.components.common.decorators;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import qube.qai.services.implementation.SearchResult;

public class MolecularInfoDecorator extends BaseDecorator {

    private Image iconImage;

    private String name = "Molecular info";

    public MolecularInfoDecorator() {
        iconImage = new Image(name,
                new ClassResource("gui/images/helix-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        VerticalLayout layout = new VerticalLayout();

        Label nameLabel = new Label("Name: " + toDecorate.getTitle());
        layout.addComponent(nameLabel);

        Label descriptionLabel = new Label("Description: " + toDecorate.getDescription());
        layout.addComponent(descriptionLabel);

        setContent(layout);
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