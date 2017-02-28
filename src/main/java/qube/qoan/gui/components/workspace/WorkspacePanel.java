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

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.*;

/**
 * Created by rainbird on 11/14/15.
 */
public class WorkspacePanel extends Panel {

    private String title;

    private AbsoluteLayout layout;

    public DragAndDropWrapper layoutWrapper;

    public WorkspacePanel(String title) {
        this.title = title;

        initialize();
    }

    private void initialize() {

        layout = new AbsoluteLayout();
        int width = UI.getCurrent().getPage().getBrowserWindowWidth();
        int height = UI.getCurrent().getPage().getBrowserWindowHeight();

        layout.setWidth(width + "px");
        layout.setHeight(height + "px");

        Label titleLabel = new Label(title);
        DragAndDropWrapper wrapper = new DragAndDropWrapper(titleLabel);
        wrapper.setSizeUndefined();
        wrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
        layout.addComponent(wrapper, "right: 50px; top: 50px;");

        layoutWrapper = new DragAndDropWrapper(layout);
        //layoutWrapper.setDropHandler(new MoveHandler());
        // and finally set the
        setContent(layoutWrapper);
    }

    public void setDropHandler(DropHandler dropHandler) {
        layoutWrapper.setDropHandler(dropHandler);
    }

    public AbsoluteLayout getBaseLayout() {
        return layout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
