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

package org.qoan.gui.components.common.search;

import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.dnd.DragSourceExtension;
import org.qai.services.SearchResultSink;
import org.qai.services.implementation.SearchResult;
import org.qoan.gui.components.common.SearchSettings;

import java.util.Collection;
import java.util.Set;

/**
 * Created by rainbird on 6/27/17.
 */
public abstract class SearchSinkComponent extends Panel implements SearchResultSink {

    protected String name;

    protected String context;

    protected Grid<SearchResult> resultGrid;

    protected CheckBox clearResults;

    protected DragSourceExtension<Grid<SearchResult>> dragExtension;

    public SearchSinkComponent() {

    }

    protected abstract void initializeSearchSettings();

    protected abstract Grid createGrid();

    public abstract void doSearch(String searchString);

    public abstract SearchSettings getSettingsFor(String serviceName);

    public abstract void delayedResults(Collection<SearchResult> results);

    protected abstract void onClearResults();

    /**
     * initialize the thing only when you actually will need it
     */
    public void initialize() {

        initializeSearchSettings();
        resultGrid = createGrid();
        dragExtension = createDragSource(resultGrid);

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        layout.addComponent(resultGrid);

        clearResults = new CheckBox("Clear results before adding new ones");
        clearResults.setValue(false);
        layout.addComponent(clearResults);

        Button clearButton = new Button("Clear results");
        clearButton.addClickListener(clickEvent -> onClearResults());
        clearButton.setStyleName("link");
        layout.addComponent(clearButton);
    }

    /**
     * add the drag-extension to the grid
     * @param grid
     * @return
     */
    protected DragSourceExtension<Grid<SearchResult>> createDragSource(Grid<SearchResult> grid) {

        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        GridDragSource<SearchResult> dragSource = new GridDragSource<>(grid);
        dragSource.setEffectAllowed(EffectAllowed.MOVE);

        dragSource.addDragStartListener(event -> {
//            Set<SearchResult> selectedItems = grid.getSelectedItems();
//            XStream xStream = new XStream();
//            String xmlSet = xStream.toXML(selectedItems);
//            dragSource.setDataTransferText(xmlSet);
//            Notification.show("Transferring: " + xmlSet);
        });

        dragSource.addDragEndListener(event -> {
            if (event.isCanceled()) {
                Notification.show("Drag event was canceled");
            } else {
                Set<SearchResult> results = grid.getSelectedItems();
                StringBuffer buffer = new StringBuffer("Selected items: <");
                for (SearchResult result : results) {
                    buffer.append(result.getTitle());
                    buffer.append("/");
                }
                buffer.append("> are added to workspace");
                Notification.show(buffer.toString());
            }
        });

        dragSource.addGridDragStartListener(event -> {
            // Keep reference to the dragged items
            //Set<SearchResult> resultSet = event.getDraggedItems();
        });

        // Add drag end listener
        dragSource.addGridDragEndListener(event -> {
            // If drop was successful, remove dragged items from source Grid
            if (event.getDropEffect() == DropEffect.MOVE) {
                //((ListDataProvider<SearchResult>) grid.getDataProvider()).getItems().removeAll(draggedItems);
                //grid.getDataProvider().refreshAll();
                // Remove reference to dragged items
                //draggedItems = null;
            }
        });

        return dragSource;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
