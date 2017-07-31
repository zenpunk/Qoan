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

package qube.qoan.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import qube.qoan.authentication.TestQoanSecurity;
import qube.qoan.authentication.TestUserManager;
import qube.qoan.gui.components.TestWikiSearchMenu;
import qube.qoan.gui.components.common.TestQoanMenu;
import qube.qoan.gui.components.common.decorators.TestDecorators;
import qube.qoan.gui.components.management.TestManagementPanel;
import qube.qoan.gui.components.workspace.finance.parser.TestWikiArticleIntegration;
import qube.qoan.gui.components.workspace.procedure.decorators.TestProcedureDecorators;
import qube.qoan.services.implementation.TestDistributedSearchServices;
import qube.qoan.util.TestGsonSerializer;

/**
 * Created by rainbird on 5/26/16.
 */
public class AllQoanTests extends TestCase {

    /**
     * shame really that there are not more tests, i suppose
     */
    public static Test suite() {

        TestSuite suite = new TestSuite("All Qoan tests");

        // user manager tests
        suite.addTestSuite(TestUserManager.class);
        suite.addTestSuite(TestQoanSecurity.class);

        // this one tests search menu drag-n-drop things
        suite.addTestSuite(TestWikiSearchMenu.class);

        // the menu components are all the same now
        suite.addTestSuite(TestQoanMenu.class);

        // management panel tests
        suite.addTestSuite(TestManagementPanel.class);

        // this one is for parsing wiki-tables
        suite.addTestSuite(TestWikiArticleIntegration.class);

        // this is the most important of all, really, to see if you have live-data
        suite.addTestSuite(TestDistributedSearchServices.class);

        // test for the decorators
        suite.addTestSuite(TestDecorators.class);
        suite.addTestSuite(TestProcedureDecorators.class);

        // Gson serialization experiments
        suite.addTestSuite(TestGsonSerializer.class);

        return suite;
    }

    public static void main(String[] params) {
        String[] tests = {AllQoanTests.class.getName()};
        TestRunner.main(tests);
    }
}
