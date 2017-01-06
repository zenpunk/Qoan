package qube.qoan.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import qube.qoan.gui.components.TestSearchMenu;
import qube.qoan.gui.components.workspace.finance.parser.TestWikiArticleIntegration;

/**
 * Created by rainbird on 5/26/16.
 */
public class AllQoanTests extends TestCase {

    /**
     * shame really that there are not more tests, i suppose
     * @TODO think of some ways of gui-testing
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("All tests");

        // this one tests search menu drag-n-drop things
        suite.addTestSuite(TestSearchMenu.class);

        // this one is for parsing wiki-tables
        suite.addTestSuite(TestWikiArticleIntegration.class);

        return suite;
    }

    public static void main(String[] params) {
        String[] tests = {AllQoanTests.class.getName()};
        TestRunner.main(tests);
    }
}
