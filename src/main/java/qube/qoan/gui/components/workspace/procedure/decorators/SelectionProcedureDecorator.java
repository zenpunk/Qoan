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

package qube.qoan.gui.components.workspace.procedure.decorators;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.procedure.Procedure;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.decorators.BaseDecorator;

import javax.inject.Inject;

public class SelectionProcedureDecorator extends BaseDecorator {

    @Inject
    private QaiDataProvider<Procedure> qaiDataProvider;

    private Image iconImage;

    /**
     * this procedure decorators will accepting drops from search results
     * to use those as input-parameters for the procedure templates.
     */
    public SelectionProcedureDecorator() {
        iconImage = new Image("Selection Procedure",
                new ClassResource("gui/images/proc.png"));
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        Procedure procedure = qaiDataProvider.brokerSearchResult(toDecorate);
        //MetricsPanel metrics = new MetricsPanel(toDecorate.getTitle(), procedure);
    }

    @Override
    public String getName() {
        return "Selection Procedure";
    }
}
