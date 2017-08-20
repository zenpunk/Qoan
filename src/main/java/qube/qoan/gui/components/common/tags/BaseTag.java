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
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.DragSourceExtension;
import qube.qai.main.QaiConstants;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.common.DisplayPanel;
import qube.qoan.gui.components.common.decorators.Decorator;

import java.util.Map;
import java.util.TreeMap;

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
        decorators = new TreeMap<>();
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

        // create and add the close-button first
        Label title = new Label(searchResult.getTitle());
        title.setStyleName("bold");
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
        TabSheet content = new TabSheet();
        if (decorators.size() > 0) {
            for (String name : decorators.keySet()) {
                Decorator decorator = decorators.get(name);
                injector.injectMembers(decorator);
                decorator.decorate(searchResult);
                content.addTab(decorator, decorator.getName(), decorator.getIconImage().getSource());
            }
        } else {
            content.addTab(new Panel("No decorators for this"));
        }

        DisplayPanel panel = new DisplayPanel(searchResult.getTitle(), parentLayout, content);
        panel.setWidth("800px");
        panel.setHeight("600px");
        DragSourceExtension<DisplayPanel> dragSourceExtension = panel.getDragExtension();

        panelWrapper = new ResizableCssLayout(panel);
        panel.setResizeWrapper(panelWrapper);

        parentLayout.addComponent(panelWrapper);

    }

    /**
     * remove the tag from workspace
     */
    public void onRemoveClicked() {
        if (parentLayout != null) {
            parentLayout.removeComponent(this);
        }
    }

    public DragSourceExtension<BaseTag> getDragExtension() {
        DragSourceExtension<BaseTag> dragExtension = new DragSourceExtension<BaseTag>(this);

        dragExtension.addDragStartListener(event -> {
            Notification.show("Drag started");
        });

        dragExtension.addDragEndListener(event -> {
            if (event.getDropEffect() == DropEffect.MOVE) {
                Notification.show("Drag MOVE finished");
            } else if (event.getDropEffect() == DropEffect.NONE) {
                Notification.show("Drag NONE finished");
            }
        });

        return dragExtension;
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
