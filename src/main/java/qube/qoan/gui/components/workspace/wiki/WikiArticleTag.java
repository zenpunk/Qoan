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

import com.vaadin.ui.*;
import qube.qai.persistence.WikiArticle;

/**
 * Created by rainbird on 11/13/15.
 */
public class WikiArticleTag extends Panel {

    private String source;

    private int top = 100;

    private int left = 100;

    private WikiArticle wikiArticle;

    private Layout parentLayout;

    public WikiArticleTag(String source, WikiArticle wikiArticle, Layout parentLayout) {
        super();

        this.source = source;
        this.wikiArticle = wikiArticle;
        this.parentLayout = parentLayout;

        initialize();
    }

    private void initialize() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        final String title = wikiArticle.getTitle();
        Label titleLabel = new Label(title);
        titleLabel.setStyleName("bold");
        layout.addComponent(titleLabel);

        Label sourceLabel = new Label("from: " + source);
        layout.addComponent(sourceLabel);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button open = new Button("open");
//        open.addClickListener(new Button.ClickListener() {
//            public void buttonClick(Button.ClickEvent event) {
//                WikiContentPanel contentPanel = new WikiContentPanel(wikiArticle);
//                contentPanel.setSizeFull();
//
//                //Window window = new Window(title);
//                final InnerWindow window = new InnerWindow(title, contentPanel);
////                window.setWidth("600px");
////                window.setHeight("400px");
//                // if toDecorate is an absolute layout, we need a position to add the thing as well
//                if (parentLayout instanceof AbsoluteLayout) {
//                    left = left + 5;
//                    top = top + 5;
//                    String positionString = "left: " + left + "px; top: " + top + "px;";
//                    DragAndDropWrapper dndWrapper = new DragAndDropWrapper(window);
//                    dndWrapper.setSizeUndefined();
//                    dndWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
//                    ((AbsoluteLayout) parentLayout).addComponent(dndWrapper, positionString);
//                } else {
//                    parentLayout.addComponent(window);
//                }
//
//            }
//        });
        open.setStyleName("link");
        buttonsLayout.addComponent(open);

        // add a button to remove this tag from workspace
        Button remove = new Button("remove");
        remove.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // we are removing the toDecorate- which is the dnd-wrapper
                Component parent = getParent();
                parentLayout.removeComponent(parent);
            }
        });
        remove.setStyleName("link");
        buttonsLayout.addComponent(remove);

        layout.addComponent(buttonsLayout);

        setContent(layout);
    }
}
