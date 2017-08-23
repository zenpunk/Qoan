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
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Image;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.dnd.DropTargetExtension;
import qube.qai.procedure.utils.SelectionProcedure;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.tags.BaseTag;

import java.util.Optional;
import java.util.Set;

public class SelectionDecorator extends BaseDecorator {

    private Image iconImage;

    private SelectionProcedure selection;

    private String name;

    private TreeGrid<SearchResult> grid;

    public SelectionDecorator(String name, SelectionProcedure selection) {
        this.name = "Selection for " + name;
        this.selection = selection;
        this.iconImage = new Image(name,
                new ClassResource("gui/images/selection-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        String mimeType = selection.getValueTo().getMimeType();

        grid = new TreeGrid<SearchResult>();
        grid.setCaption("Drop searches from Finance and Stock Searches");
        grid.addColumn(SearchResult::getContext).setCaption("Context");
        grid.addColumn(SearchResult::getTitle).setCaption("Title");
        grid.addColumn(SearchResult::getDescription).setCaption("Description");
        grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        grid.addColumn(SearchResult::getUuid).setCaption("UUID");
        grid.setWidth("100%");
        grid.setHeight("100%");

        setContent(grid);

        SelectionDropListener listener = new SelectionDropListener(grid);
        listener.addDropListener(event -> {
            Optional<AbstractComponent> dragSource = event.getDragSourceComponent();

            if (dragSource.isPresent() && dragSource.get() instanceof Grid) {
                Grid source = (Grid) dragSource.get();
                Set<SearchResult> results = source.getSelectedItems();
                grid.setItems(results);
                grid.getDataProvider().refreshAll();
            } else if (dragSource.isPresent() && dragSource.get() instanceof BaseTag) {
                BaseTag tag = (BaseTag) dragSource.get();
                SearchResult result = tag.getSearchResult();
                grid.setItems(result);
                grid.getDataProvider().refreshAll();
            }

        });
    }

    public void addListener() {

    }

    class SelectionDropListener extends DropTargetExtension<Grid> {
        public SelectionDropListener(Grid target) {
            super(target);
        }
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
