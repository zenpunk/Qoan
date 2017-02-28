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

package qube.qoan.gui.components;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import qube.qoan.gui.components.workspace.WorkSpace;
import qube.qoan.gui.components.workspace.search.SearchMenu;
import qube.qoan.services.QoanTestBase;

import java.util.Collection;

/**
 * Created by rainbird on 11/19/15.
 */
public class TestSearchMenu extends QoanTestBase {


    public void testSearchMenuDropAction() throws Exception {

        // @TODO add the proper test here
        SearchMenu searchMenu = new SearchMenu();

        WorkSpace workspace = new WorkSpace(searchMenu);

        AbsoluteLayout layout = new AbsoluteLayout();
        DropHandler dropHandler = workspace.createDropHandler(layout);
        Transferable transferable = new Transferable() {
            public Object getData(String dataFlavor) {
                return null;
            }

            public void setData(String dataFlavor, Object value) {

            }

            public Collection<String> getDataFlavors() {
                return null;
            }

            public Component getSourceComponent() {
                return null;
            }
        };
        DragAndDropEvent event = new DragAndDropEvent(transferable, null);
        dropHandler.drop(event);
        // @TODO is there anything we need to check really
        //fail("implementation details missing!!!");
    }
}
