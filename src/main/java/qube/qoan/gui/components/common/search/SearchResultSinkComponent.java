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

package qube.qoan.gui.components.common.search;

import com.thoughtworks.xstream.XStream;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.dnd.DragSourceExtension;
import qube.qai.services.SearchResultSink;
import qube.qai.services.implementation.SearchResult;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rainbird on 6/27/17.
 */
public abstract class SearchResultSinkComponent extends Panel implements SearchResultSink {

    protected Grid<SearchResult> resultGrid;

    protected List<SearchResult> searchResults;

    protected CheckBox clearResults;

    public SearchResultSinkComponent() {
        initialize();
    }

    protected abstract void initializeSearchResults();

    protected abstract Grid createGrid(Collection<SearchResult> results);

    /**
     * initialize the thing only when you actually will need it
     */
    public void initialize() {

        initializeSearchResults();
        resultGrid = createGrid(searchResults);

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        layout.addComponent(resultGrid);

        clearResults = new CheckBox("Clear results before adding new ones");
        layout.addComponent(clearResults);

        Button clearButton = new Button("Clear results");
        clearButton.addClickListener(clickEvent -> searchResults.clear());
        clearButton.setStyleName("link");
        layout.addComponent(clearButton);
    }

    /**
     * @param grid
     * @return
     */
    protected DragSourceExtension<Grid<SearchResult>> createDragSource(Grid<SearchResult> grid) {

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        final Set<SearchResult> draggedItems = new HashSet<>();

        //DragSourceExtension<Grid<SearchResult>> dragSource = new DragSourceExtension<>(grid);
        GridDragSource<SearchResult> dragSource = new GridDragSource<>(grid);
        dragSource.setEffectAllowed(EffectAllowed.MOVE);

        dragSource.addDragStartListener(event -> {

            Set<SearchResult> selectedItems = grid.getSelectedItems();
            XStream xStream = new XStream();
            String xmlSet = xStream.toXML(selectedItems);
            dragSource.setDataTransferText(xmlSet);
            Notification.show("Transferring: " + xmlSet);
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
            Set<SearchResult> resultSet = event.getDraggedItems();
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

    public void addResults(Collection<SearchResult> results) {

        // if there are no results, don't bother add them
        if (results == null || results.isEmpty()) {
            return;
        }

        // @TODO i hope i can change this soon back to what it should be
        Window window = new Window("Search results: " + results.size() + " of them...");
        Grid<SearchResult> grid = createGrid(results);
        window.setContent(grid);
        window.setWidth("800px");
        window.setHeight("600px");

        UI.getCurrent().addWindow(window);

//        if (clearResults.getValue()) {
//            searchResults.clear();
//        }
//        // updating the grid has a problem...
//        Notification.show("Adding " + results.size() + " rows for display");
//        searchResults.addAll(results);
//        resultGrid.getDataProvider().refreshAll();
//        //initialize();
    }
}
