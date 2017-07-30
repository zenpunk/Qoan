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
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.workspace.wiki.WikiContentPanel;

import javax.inject.Inject;

/**
 * Created by rainbird on 7/7/17.
 */
public class WikiDecorator extends BaseDecorator {

    @Inject
    private QaiDataProvider<WikiArticle> qaiDataProvider;

    private Image iconImage;

    public WikiDecorator() {
        iconImage = new Image("Wiki article",
                new ClassResource("gui/images/wiki.png"));
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public void decorate(SearchResult searchResult) {

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        WikiArticle wikiArticle = qaiDataProvider.brokerSearchResult(searchResult);
        final String title = wikiArticle.getTitle();
        Label titleLabel = new Label(title);
        titleLabel.setStyleName("bold");
        layout.addComponent(titleLabel);

        Label contextLabel = new Label("Context: " + searchResult.getContext());
        layout.addComponent(contextLabel);

        WikiContentPanel contentPanel = new WikiContentPanel(wikiArticle);
        contentPanel.setSizeUndefined();
        layout.addComponent(contentPanel);

        setContent(layout);

    }
}
