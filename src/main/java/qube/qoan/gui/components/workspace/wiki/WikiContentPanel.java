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
import com.vaadin.ui.*;
import info.bliki.wiki.model.WikiModel;
import qube.qai.network.Network;
import qube.qai.network.semantic.SemanticNetwork;
import qube.qai.persistence.WikiArticle;
import qube.qoan.gui.components.workspace.network.NetworkPanel;

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
        VerticalLayout layout = new VerticalLayout();

        final TabSheet tabbedContent = new TabSheet();
        tabbedContent.setSizeFull();
        String content = WikiModel.toHtml(wikiArticle.getContent());
        Label contentText = new Label(content, ContentMode.HTML);
        Panel contentPanel = new Panel(contentText);
        tabbedContent.addTab(contentPanel).setCaption("Wiki-Article");
        //layout.addComponent(tabbedContent);

        HorizontalLayout buttonLayout = new HorizontalLayout();

        final Button addWikiNetworkButton = new Button("Create Wiki-Network");
        addWikiNetworkButton.setStyleName("link");
        addWikiNetworkButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Network network = Network.createTestNetwork();
                NetworkPanel networkPanel = new NetworkPanel(network);
                networkPanel.setSizeFull();
                tabbedContent.addTab(networkPanel).setCaption("Wiki-Network");
                wikiGraphAdded = true;
                addWikiNetworkButton.setVisible(!wikiGraphAdded);
            }
        });
        buttonLayout.addComponent(addWikiNetworkButton);

        final Button addSemanticNetwork = new Button("Create Semantic-Network");
        addSemanticNetwork.setStyleName("link");
        addSemanticNetwork.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                SemanticNetwork semanticNetwork = new SemanticNetwork();
                //semanticNetwork.buildNetwork(wikiArticle);
                NetworkPanel networkPanel = new NetworkPanel(semanticNetwork);
                networkPanel.setSizeFull();
                tabbedContent.addTab(networkPanel).setCaption("Semantic-Network");
                semanticGraphAdded = true;
                addSemanticNetwork.setVisible(!semanticGraphAdded);
            }
        });
        buttonLayout.addComponent(addSemanticNetwork);


        // add the components to layout
        layout.addComponent(buttonLayout);
        layout.addComponent(tabbedContent);
        setContent(layout);
    }
}
