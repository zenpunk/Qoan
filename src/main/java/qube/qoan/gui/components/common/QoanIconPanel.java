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

package qube.qoan.gui.components.common;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;

public class QoanIconPanel extends Panel {

    public QoanIconPanel() {

        ClassResource resource = new ClassResource("gui/images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        setContent(image);

    }
}
