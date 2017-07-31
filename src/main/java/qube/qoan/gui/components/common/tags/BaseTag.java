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

import com.google.inject.Injector;
import com.vaadin.pekka.resizablecsslayout.ResizableCssLayout;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import qube.qai.main.QaiConstants;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.common.WorkspacePanel;
import qube.qoan.gui.components.common.decorators.Decorator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainbird on 7/6/17.
 */
public class BaseTag extends Panel implements QoanTag, QaiConstants {

    protected Layout parentLayout;

    protected SearchResult searchResult;

    protected Layout layout;

    protected Image iconImage;

    private ResizableCssLayout panelWrapper;

    protected Map<String, Decorator> decorators;

    private BaseTag() {
        decorators = new HashMap<>();
    }

    public BaseTag(Layout parentLayout, SearchResult searchResult) {
        this();
        this.parentLayout = parentLayout;
        this.searchResult = searchResult;
    }

    /**
     * do whatever there is in way of display here
     */
    public void initialize() {

        // if icon-image has not already been initialized, put something there.
        if (iconImage == null) {
            iconImage = new Image(WIKIPEDIA_RESOURCES,
                    new ClassResource("gui/images/readings.png"));
        }

        iconImage.setWidth("60px");
        iconImage.setHeight("60px");

        layout = new VerticalLayout();
        layout.addComponent(iconImage);

        Label title = new Label("Title: '" + searchResult.getTitle() + "'");
        layout.addComponent(title);

        HorizontalLayout buttonsRow = new HorizontalLayout();

        Button openButton = new Button("Open");
        openButton.addClickListener(clickEvent -> onOpenClicked());
        openButton.setStyleName("link");
        buttonsRow.addComponent(openButton);

        Button removeButton = new Button("Remove");
        removeButton.addClickListener(clickEvent -> onRemoveClicked());
        removeButton.setStyleName("link");
        buttonsRow.addComponent(removeButton);
        layout.addComponent(buttonsRow);

        setContent(layout);
        setWidth("200px");
        setHeight("200px");
    }

    /**
     * open the window with the assigned decorators, basicly
     */
    public void onOpenClicked() {

        Injector injector = ((QoanUI) QoanUI.getCurrent()).getInjector();
        Component content;
        if (decorators.size() > 1) {
            content = new TabSheet();
            for (String name : decorators.keySet()) {
                Decorator decorator = decorators.get(name);
                injector.injectMembers(decorator);
                decorator.decorate(searchResult);
                ((TabSheet) content).addTab(decorator, decorator.getCaption(), decorator.getIcon());
            }
        } else if (decorators.size() == 1) {
            Decorator decorator = decorators.values().iterator().next();
            injector.injectMembers(decorator);
            decorator.decorate(searchResult);
            content = decorator;
        } else {
            content = new Panel("No decorators for this");
        }

        WorkspacePanel panel = new WorkspacePanel(searchResult.getTitle(), parentLayout, content);
        panel.setWidth("800px");
        panel.setHeight("400px");

//        ResizableCssLayout panelWrapper = new ResizableCssLayout();
//        panelWrapper.setResizable(true);
//        panelWrapper.setHeight("800px");
//        panelWrapper.setWidth("400px");
//        panelWrapper.addComponent(panel);
//        panelWrapper.setCaption("Resize from panel's edges");

        parentLayout.addComponent(panel);

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
