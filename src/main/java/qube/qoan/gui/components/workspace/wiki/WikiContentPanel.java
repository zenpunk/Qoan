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

package qube.qoan.gui.components.workspace.wiki;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import info.bliki.wiki.model.WikiModel;
import qube.qai.persistence.WikiArticle;

/**
 * Created by rainbird on 11/21/15.
 */
public class WikiContentPanel extends Panel {

    private boolean wikiGraphAdded = false;
    private boolean semanticGraphAdded = false;

    private WikiArticle wikiArticle;

    public WikiContentPanel(WikiArticle wikiArticle) {
        this.wikiArticle = wikiArticle;

        initialize(wikiArticle);
    }

    private void initialize(WikiArticle wikiArticle) {

        //VerticalLayout layout = new VerticalLayout();
        //layout.setHeight("100%");
        //layout.setWidth("100%");

        //final TabSheet tabbedContent = new TabSheet();
        //tabbedContent.setSizeFull();
        String content = WikiModel.toHtml(wikiArticle.getContent());
        Label contentText = new Label(content, ContentMode.HTML);
        contentText.setStyleName("text-align: justify");
        //layout.addComponent(contentText);

        //setWidth("100%");
        //setHeight("90%");
        setContent(contentText);

    }
}
