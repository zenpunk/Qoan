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

import com.google.inject.Injector;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TreeGrid;
import qube.qai.main.QaiConstants;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.ProcedureLibrary;
import qube.qai.procedure.ProcedureTemplate;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.QoanUI;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Created by rainbird on 7/4/17.
 */
public class ProcedureSearchSink extends SearchSinkComponent {

    @Inject
    @Named("Procedures")
    private SearchServiceInterface searchService;

    @Inject
    private QaiDataProvider<Procedure> dataProvider;

    @Override
    protected void initializeSearchResults() {

        // self-inoculation
        Injector injector = ((QoanUI) QoanUI.getCurrent()).getInjector();
        injector.injectMembers(this);

        for (ProcedureTemplate template : ProcedureLibrary.allTemplates) {
            Procedure proc = template.createProcedure();
            Collection<SearchResult> results = searchService.searchInputString(proc.getProcedureName(), QaiConstants.PROCEDURES, 100);
            SearchResult procResult = new SearchResult(QaiConstants.PROCEDURES, proc.getProcedureName(), "", proc.getDescriptionText(), 1.0);
            TreeDataProvider<SearchResult> gridDataProvider = (TreeDataProvider<SearchResult>) ((TreeGrid<SearchResult>) resultGrid).getDataProvider();
            TreeData<SearchResult> data = gridDataProvider.getTreeData();
            data.addItem(null, procResult);
            // if the results have returned nothing just go on tot eh next procedure.
            if (results == null || results.isEmpty()) {
                continue;
            } else {
                data.addItems(procResult, results);
                gridDataProvider.refreshAll();
            }
        }

    }

    @Override
    protected Grid createGrid() {

        TreeGrid<SearchResult> grid = new TreeGrid<>();
        grid.addColumn(SearchResult::getContext).setCaption("Context");
        grid.addColumn(SearchResult::getTitle).setCaption("Title");
        grid.addColumn(SearchResult::getDescription).setCaption("Description");
        grid.addColumn(SearchResult::getRelevance).setCaption("Relevance");
        grid.addColumn(SearchResult::getUuid).setCaption("UUID");
        grid.setWidth("100%");
        grid.setHeight("100%");
        return grid;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void addResults(Collection<SearchResult> results) {
        // do nothing- the initialization at start should be sufficient
        //super.addResults(results);
    }
}