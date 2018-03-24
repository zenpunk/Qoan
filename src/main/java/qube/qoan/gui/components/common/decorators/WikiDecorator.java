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

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import info.bliki.wiki.model.WikiModel;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.implementation.SearchResult;

import javax.inject.Inject;

/**
 * Created by rainbird on 7/7/17.
 */
@JavaScript({"MediaWiki_Common.js", "MathJax.js"})
public class WikiDecorator extends BaseDecorator {

    @Inject
    private QaiDataProvider<WikiArticle> qaiDataProvider;

    private String mathJaxInit = "MathJax.js?config=TeX-AMS-MML_HTMLorMML";

    private String addStyleLine = "<link rel='stylesheet' type='text/css' href='/VAADIN/themes/mytheme/MediaWiki_Common.css'>";

    private Image iconImage;

    public WikiDecorator() {
        iconImage = new Image("Wiki article",
                new ClassResource("gui/images/wiki-icon.png"));
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public void decorate(SearchResult searchResult) {

        WikiArticle wikiArticle = qaiDataProvider.brokerSearchResult(searchResult);
        if (wikiArticle == null) {
            Notification.show("Strange- the wiki-article could not be found!");
            return;
        }
        String content = addStyleLine + "\n" + WikiModel.toHtml(wikiArticle.getContent());
        Label contentText = new Label(content, ContentMode.HTML);
        contentText.setWidth("780px");
        contentText.setStyleName("justify");
        //setWidth("795px");
        setContent(contentText);
        com.vaadin.ui.JavaScript.getCurrent().execute(mathJaxInit);
    }

    @Override
    public String getName() {
        return "Wiki Article";
    }
}
