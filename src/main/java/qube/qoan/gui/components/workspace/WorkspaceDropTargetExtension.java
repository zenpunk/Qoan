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

package qube.qoan.gui.components.workspace;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.dnd.DropTargetExtension;
import qube.qai.main.QaiConstants;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.tags.*;

import java.util.Optional;
import java.util.Set;

/**
 * Created by rainbird on 7/6/17.
 */
public class WorkspaceDropTargetExtension extends DropTargetExtension<AbsoluteLayout>
        implements QaiConstants {

    private AbsoluteLayout targetLayout;

    public WorkspaceDropTargetExtension(AbsoluteLayout target) {
        super(target);
        targetLayout = target;
    }

    public void addListener() {
        addDropListener(event -> {
            // if the drag source is in the same UI as the target
            Optional<AbstractComponent> dragSource = event.getDragSourceComponent();
            if (dragSource.isPresent() && dragSource.get() instanceof Grid) {

                String dropCoords = "left: %d px; top: %d px;";
                int dropX = event.getMouseEventDetails().getRelativeX();
                int dropY = event.getMouseEventDetails().getRelativeY();

                Grid grid = (Grid) dragSource.get();
                Set<SearchResult> results = grid.getSelectedItems();
                QoanTag tag;
                for (SearchResult result : results) {
                    tag = createQoanTag(result);
                    String coords = String.format(dropCoords, dropX, dropY);
                    targetLayout.addComponent(tag, coords);
                }
            }
        });
    }

    protected QoanTag createQoanTag(SearchResult result) {

        QoanTag tag;

        if (WIKIPEDIA.equals(result.getContext())) {
            tag = new WikiArticleTag(result);
        } else if (WIKTIONARY.equals(result.getContext())) {
            tag = new WikiArticleTag(result);
        } else if (STOCK_ENTITIES.equals(result.getContext())) {
            tag = new StockEntityTag(result);
        } else if (PROCEDURES.equals(result.getContext())) {
            tag = new ProcedureTag(result);
        } else if (WIKIPEDIA_RESOURCES.equals(result.getContext())) {
            tag = new ResourceTag(result);
        } else {
            tag = new BaseTag(result);
        }

        tag.initialize();

        return tag;
    }
}
