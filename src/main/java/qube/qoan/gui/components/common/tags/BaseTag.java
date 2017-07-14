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
import com.vaadin.ui.*;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.InnerWindow;
import qube.qoan.gui.components.common.decorators.BaseDecorator;
import qube.qoan.gui.components.common.decorators.Decorator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainbird on 7/6/17.
 */
public class BaseTag extends Panel implements QoanTag {

    protected Layout parentLayout;

    protected SearchResult searchResult;

    protected Layout layout;

    protected Image iconImage;

    protected Map<String, Decorator> decorators;

    private BaseTag() {
        decorators = new HashMap<>();
    }

    public BaseTag(SearchResult searchResult) {
        this();
        this.searchResult = searchResult;
    }

    /**
     * do whatever there is in way of display here
     */
    public void initialize() {

        // i fthis has not already been initialized, put something there.
        if (iconImage == null) {
            iconImage = new Image("Resources",
                    new ClassResource("qube/qoan/images/readings.png"));
        }
        layout = new VerticalLayout();

        HorizontalLayout imageRow = new HorizontalLayout();
        imageRow.addComponent(iconImage);

        VerticalLayout labelsPart = new VerticalLayout();

        Label context = new Label("Context: " + searchResult.getContext() + "'");
        labelsPart.addComponent(context);

        Label title = new Label("Title: " + searchResult.getTitle() + "'");
        labelsPart.addComponent(title);

        Label uuid = new Label("UUID: " + searchResult.getUuid() + "'");
        labelsPart.addComponent(uuid);

        imageRow.addComponent(labelsPart);
        layout.addComponent(imageRow);

        HorizontalLayout buttonsRow = new HorizontalLayout();

        Button openButton = new Button("Open");
        openButton.addClickListener(clickEvent -> onOpenClicked());
        buttonsRow.addComponent(openButton);

        Button removeButton = new Button("Remove");
        removeButton.addClickListener(clickEvent -> onRemoveClicked());
        buttonsRow.addComponent(removeButton);
        layout.addComponent(buttonsRow);

        setContent(layout);
    }

    /**
     * open the window with the assigned decorators, basicly
     */
    public void onOpenClicked() {

        BaseDecorator baseDecorator = new BaseDecorator();

        for (String name : decorators.keySet()) {
            baseDecorator.addDecorator(name, decorators.get(name));
        }

        baseDecorator.decorateAll(searchResult);
        InnerWindow window = new InnerWindow("", baseDecorator);
        parentLayout.addComponent(window);

    }

    /**
     * remove the tag from workspace
     */
    public void onRemoveClicked() {
        if (parentLayout != null) {
            parentLayout.removeComponent(this);
        }
    }

    @Override
    public SearchResult getSearchResult() {
        return searchResult;
    }

    @Override
    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    @Override
    public void setParentLayout(Layout parentLayout) {
        this.parentLayout = parentLayout;
    }
}
