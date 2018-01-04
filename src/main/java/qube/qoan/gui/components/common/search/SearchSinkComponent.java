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

package qube.qoan.gui.components.common.search;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.dnd.DragSourceExtension;
import qube.qai.services.SearchResultSink;
import qube.qai.services.implementation.SearchResult;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rainbird on 6/27/17.
 */
public abstract class SearchSinkComponent extends Panel implements SearchResultSink {

    protected Grid<SearchResult> resultGrid;

    protected Collection<SearchResult> searchResults;

    protected ListDataProvider<SearchResult> dataProvider;

    protected CheckBox clearResults;

    protected DragSourceExtension<Grid<SearchResult>> dragExtension;

    public SearchSinkComponent() {
        searchResults = new ArrayList<>();
        dataProvider = DataProvider.ofCollection(searchResults);
    }

    protected abstract void initializeSearchResults();

    protected abstract Grid createGrid();

    public abstract void addResults(Collection<SearchResult> results);

    /**
     * initialize the thing only when you actually will need it
     */
    public void initialize() {

        resultGrid = createGrid();
        dragExtension = createDragSource(resultGrid);
        initializeSearchResults();

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

    public void onClearResults() {
        searchResults.clear();
        dataProvider.refreshAll();
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
                Notification.show("Drag event finished");
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
}
