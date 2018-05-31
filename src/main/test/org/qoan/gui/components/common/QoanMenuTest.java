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

package org.qoan.gui.components.common;

import org.qai.services.implementation.SearchResult;
import org.qoan.gui.components.workspace.finance.FinanceMenu;
import org.qoan.gui.components.workspace.procedure.ProcedureMenu;
import org.qoan.gui.components.workspace.resource.ResourceMenu;
import org.qoan.gui.components.workspace.wiki.WikiMenu;
import org.qoan.services.QoanInjectorService;
import org.qoan.services.QoanTestBase;

import java.util.Collection;

/**
 * Created by rainbird on 7/2/17.
 */
public class QoanMenuTest extends QoanTestBase {


    public void testWikihMenu() throws Exception {

        WikiMenu wikiMenu = new WikiMenu();
        QoanInjectorService.getInstance().injectMembers(wikiMenu);

        wikiMenu.initialize();

        wikiMenu.doSearch("mouse");

        Thread.sleep(1000);

        Collection<SearchResult> results = wikiMenu.getCurrentResult();
        assertNotNull("there has to be some results", results);
        assertTrue("there has to be content in the results", !results.isEmpty());

    }

    public void testProceduresMenu() throws Exception {

        ProcedureMenu procedureMenu = new ProcedureMenu();
        QoanInjectorService.getInstance().injectMembers(procedureMenu);

        procedureMenu.initialize();

        procedureMenu.doSearch("");

        Thread.sleep(1000);

        Collection<SearchResult> results = procedureMenu.getCurrentResult();
        assertNotNull("there has to be some results", results);
        assertTrue("there has to be content in the results", !results.isEmpty());
    }

    public void testFinanceMenu() throws Exception {

        FinanceMenu financeMenu = new FinanceMenu();
        QoanInjectorService.getInstance().injectMembers(financeMenu);

        financeMenu.initialize();

        financeMenu.doSearch("");

        Thread.sleep(1000);

        Collection<SearchResult> results = financeMenu.getCurrentResult();
        assertNotNull("there has to be some results", results);
        assertTrue("there has to be content in the results", !results.isEmpty());
    }

    public void testDocumentMenu() throws Exception {

        ResourceMenu resourceMenu = new ResourceMenu();
        QoanInjectorService.getInstance().injectMembers(resourceMenu);

        resourceMenu.initialize();
        resourceMenu.doSearch("");

        Thread.sleep(1000);

        Collection<SearchResult> results = resourceMenu.getCurrentResult();
        assertNotNull("there has to be some results", results);
        assertTrue("there has to be content in the results", !results.isEmpty());
    }
}
