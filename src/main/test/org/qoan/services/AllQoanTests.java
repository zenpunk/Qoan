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

package org.qoan.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.qoan.authentication.QoanRealmTest;
import org.qoan.authentication.QoanSecurityTest;
import org.qoan.authentication.UserManagerTest;
import org.qoan.gui.components.WikiMenuTest;
import org.qoan.gui.components.common.QoanMenuTest;
import org.qoan.gui.components.common.decorators.DecoratorsTest;
import org.qoan.gui.components.management.ManagementPanelTest;
import org.qoan.gui.components.workspace.procedure.decorators.ProcedureTemplateDecoratorsTest;
import org.qoan.services.implementation.ProcedureLibraryTests;
import org.qoan.services.implementation.QaiIntegrationNetworkBuildersTest;
import org.qoan.services.implementation.QaiIntegrationSearchServicesTest;

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
        suite.addTestSuite(UserManagerTest.class);
        suite.addTestSuite(QoanSecurityTest.class);
        suite.addTestSuite(QoanRealmTest.class);

        // this one tests wiki menu drag-n-drop things
        suite.addTestSuite(WikiMenuTest.class);

        // the menu components are all the same now
        suite.addTestSuite(QoanMenuTest.class);

        // management panel tests
        suite.addTestSuite(ManagementPanelTest.class);

        // this is the most important of all, really, to see if you have live-data
        suite.addTestSuite(QaiIntegrationSearchServicesTest.class);
        suite.addTestSuite(ProcedureLibraryTests.class);

        // test for the decorators
        suite.addTestSuite(DecoratorsTest.class);
        suite.addTestSuite(ProcedureTemplateDecoratorsTest.class);
        suite.addTestSuite(QaiIntegrationNetworkBuildersTest.class);

        return suite;
    }

    public static void main(String[] params) {
        String[] tests = {AllQoanTests.class.getName()};
        TestRunner.main(tests);
    }
}
