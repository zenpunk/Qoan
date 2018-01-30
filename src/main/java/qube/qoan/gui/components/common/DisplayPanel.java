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

package qube.qoan.gui.components.common;

import com.vaadin.pekka.resizablecsslayout.ResizableCssLayout;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.DragSourceExtension;

/**
 * Created by rainbird on 12/3/15.
 */
public class DisplayPanel extends Panel {

    private String title;

    private ResizableCssLayout wrapperLayout;

    private Layout parentLayout;

    //private ResizableCssLayout resizeWrapper;

    /**
     * this class is a substitute to Vaadin's native
     * window, in order to remedy some of its drawbacks
     * InnerPanels can be opened, closed and dragged around
     * on the Workspace
     */
    public DisplayPanel(String title, Layout parentLayout, Component content) {

        super();
        this.parentLayout = parentLayout;
        this.title = title;
        initialize(content);
    }

    private void initialize(Component content) {

        VerticalSplitPanel panel = new VerticalSplitPanel();
        panel.setSplitPosition(50, Unit.PIXELS);
        panel.setLocked(true);

        HorizontalLayout titleRow = new HorizontalLayout();

        Button closeButton = new Button("X");
        closeButton.addClickListener(clickEvent -> onCLoseClicked());
        closeButton.setStyleName("link");
        titleRow.addComponent(closeButton);

        Label titleLabel = new Label(title);
        titleLabel.setStyleName("bold");
        titleRow.addComponent(titleLabel);

        panel.setFirstComponent(titleRow);
        panel.setSecondComponent(content);
        setContent(panel);

        //resizeWrapper = new ResizableCssLayout(this);
    }

    public void onCLoseClicked() {
        parentLayout.removeComponent(wrapperLayout);
        parentLayout.removeComponent(this);
    }

    public DragSourceExtension<DisplayPanel> getDragExtension() {
        DragSourceExtension<DisplayPanel> dragExtension = new DragSourceExtension<DisplayPanel>(this);

        dragExtension.addDragStartListener(event -> {
            //Notification.show("Drag started");
        });

        dragExtension.addDragEndListener(event -> {
            if (event.getDropEffect() == DropEffect.MOVE) {
                //    Notification.show("Drag MOVE finished");
            } else if (event.getDropEffect() == DropEffect.NONE) {
                //    Notification.show("Drag NONE finished");
            }
        });

        return dragExtension;
    }

    public ResizableCssLayout getWrapperLayout() {
        return wrapperLayout;
    }

    public void setWrapperLayout(ResizableCssLayout wrapperLayout) {
        this.wrapperLayout = wrapperLayout;
    }
}
