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

package qube.qoan.gui.components.common.decorators;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.NglAdapter;

/**
 * Created by rainbird on 7/8/17.
 */
public class MolecularViewerDecorator extends Panel implements Decorator {

    private Image iconImage;

    private String jsExec = "NGL.init( onInit );";

    private String jsExecParam = "NGL.init( onInitLoad('";

    public MolecularViewerDecorator() {
        iconImage = new Image("NGL-Viewer",
                new ClassResource("qube/qoan/gui/images/helix.jog"));
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        VerticalLayout layout = new VerticalLayout();

        NglAdapter nglViewer = new NglAdapter();
        layout.addComponent(nglViewer);

        HorizontalLayout selectLine = new HorizontalLayout();
        TextField nameField = new TextField("Molecule name");
        nameField.setValue("1crm");
        selectLine.addComponent(nameField);

        Button showNgl = new Button("Show molecule");
        showNgl.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String moleculeName = nameField.getValue();
                if (StringUtils.isEmpty(moleculeName)) {
                    JavaScript.getCurrent().execute(jsExec);
                } else {
                    String toExecute = jsExecParam + moleculeName + "' ))";
                    JavaScript.getCurrent().execute("alert('" + toExecute + "')");
                    JavaScript.getCurrent().execute(toExecute);
                }
            }
        });
        selectLine.addComponent(showNgl);
        layout.addComponent(selectLine);
        setContent(layout);
    }

    @Override
    public void addDecorator(String name, Decorator decorator) {

    }

    @Override
    public void decorateAll(SearchResult searchResult) {

    }
}
