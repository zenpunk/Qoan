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

package qube.qoan.gui.components.common.decorators;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Image;
import com.vaadin.ui.dnd.DropTargetExtension;
import qube.qai.procedure.utils.Select;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.tags.BaseTag;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class SelectionDecorator extends BaseDecorator {

    private Image iconImage;

    private Select select;

    private String name;

    private boolean isInitialized = false;

    private Grid<SearchResult> grid;

    private final ArrayList<SearchResult> results;

    public SelectionDecorator(String name, Select select) {
        this.name = "Selection for " + name;
        this.select = select;
        this.results = new ArrayList<>();
        this.iconImage = new Image(name,
                new ClassResource("gui/images/select-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        //String mimeType = select.getValueTo().getMimeType();

        // initialize the whole only once, so that re-opening the
        // desktop-tag doesn't remove all data
        if (!isInitialized) {

            select.setResults(results);

            ListDataProvider<SearchResult> dataProvider = DataProvider.ofCollection(results);

            grid = new Grid<>();
            grid.setCaption("Drop searches from Finance and Stock Searches");
            grid.addColumn(SearchResult::getContext).setCaption("Context");
            grid.addColumn(SearchResult::getTitle).setCaption("Title");
            grid.addColumn(SearchResult::getDescription).setCaption("Description");
            grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
            grid.addColumn(SearchResult::getUuid).setCaption("UUID");
            grid.setWidth("100%");
            grid.setHeight("100%");

            grid.setDataProvider(dataProvider);

            setContent(grid);

            SelectionDropListener listener = new SelectionDropListener(grid);
            listener.addDropListener(event -> {
                Optional<AbstractComponent> dragSource = event.getDragSourceComponent();

                if (dragSource.isPresent() && dragSource.get() instanceof Grid) {
                    Grid source = (Grid) dragSource.get();
                    Set<SearchResult> items = source.getSelectedItems();
                    results.addAll(items);
                    dataProvider.refreshAll();
                } else if (dragSource.isPresent() && dragSource.get() instanceof BaseTag) {
                    BaseTag tag = (BaseTag) dragSource.get();
                    SearchResult result = tag.getSearchResult();
                    grid.setItems(result);
                    grid.getDataProvider().refreshAll();
                }

            });

            isInitialized = true;
        }

    }

    /*public void addListener() {

    }*/

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
