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

package qube.qoan.gui.views;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.dnd.event.DropEvent;
import qube.qai.main.QaiConstants;
import qube.qai.user.User;
import qube.qoan.gui.components.common.tags.BaseTag;
import qube.qoan.gui.components.common.tags.UserTag;

import java.util.Optional;
import java.util.Set;

public class UserDropExtension extends DropTargetExtension<AbsoluteLayout>
        implements QaiConstants {

    private AbsoluteLayout targetLayout;

    public UserDropExtension(AbsoluteLayout target) {
        super(target);
        targetLayout = target;
    }

    public void addListener() {
        addDropListener(event -> onDrop(event));
    }

    protected void onDrop(DropEvent event) {

        Optional<AbstractComponent> dragSource = event.getDragSourceComponent();
        String dropCoords = "left: %d px; top: %d px;";
        int dropX = event.getMouseEventDetails().getRelativeX();
        int dropY = event.getMouseEventDetails().getRelativeY();

        if (dragSource.isPresent() && dragSource.get() instanceof Grid) {

            Grid grid = (Grid) dragSource.get();
            Set<User> users = grid.getSelectedItems();

            for (User user : users) {
                BaseTag tag = new UserTag(targetLayout, user);
                DragSourceExtension<BaseTag> dragExtension = tag.getDragExtension();
                String coords = String.format(dropCoords, dropX, dropY);
                targetLayout.addComponent(tag, coords);
            }


        } else if (dragSource.isPresent() && dragSource.get() instanceof BaseTag) {
            BaseTag tag = (BaseTag) dragSource.get();
            String coords = String.format(dropCoords, dropX, dropY);
            targetLayout.addComponent(tag, coords);
        }
    }
}
