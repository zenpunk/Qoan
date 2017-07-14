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
import qube.qai.services.implementation.SearchResult;

/**
 * Created by rainbird on 7/7/17.
 */
public class WikiDecorator extends Panel implements Decorator {

    private Image iconImage;

    public WikiDecorator() {
        iconImage = new Image("Wiki article",
                new ClassResource("qube/qoan/images/wiki.png"));
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public void decorate(SearchResult toDecorate) {

    }

    @Override
    public void addDecorator(String name, Decorator decorator) {

    }

    @Override
    public void decorateAll(SearchResult searchResult) {

    }
}
