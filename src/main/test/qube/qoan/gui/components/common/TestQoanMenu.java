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

import qube.qoan.gui.components.workspace.finance.FinanceMenu;
import qube.qoan.gui.components.workspace.procedure.ProcedureMenu;
import qube.qoan.gui.components.workspace.search.WikiSearchMenu;
import qube.qoan.services.QoanTestBase;

/**
 * Created by rainbird on 7/2/17.
 */
public class TestQoanMenu extends QoanTestBase {

    private WikiSearchMenu wikiSearchMenu;

    private ProcedureMenu procedureMenu;

    private FinanceMenu financeMenu;

    public void testSearchMenu() throws Exception {

        wikiSearchMenu = new WikiSearchMenu();
        injector.injectMembers(wikiSearchMenu);

        fail("implement first the test");
    }

    public void testProceduresMenu() throws Exception {

        procedureMenu = new ProcedureMenu();
        injector.injectMembers(procedureMenu);

        fail("implement first the test");
    }

    public void testFinanceMenu() throws Exception {

        financeMenu = new FinanceMenu();
        injector.injectMembers(financeMenu);

        fail("implement first the test");
    }
}
