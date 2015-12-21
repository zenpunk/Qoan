package qube.qoan.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * Created by rainbird on 11/19/15.
 */
public class QoanTestBase extends TestCase {

    protected Injector injector;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        injector = Guice.createInjector(new QoanTestModule());
        injector.injectMembers(this);

    }


    public static void main(String[] params) {
        String[] tests = {QoanTestBase.class.getName()};
        TestRunner.main(tests);
    }

    /*public static Test suite() {
        TestSuite suite = new TestSuite("All tests");

        // this one tests search menu drag-n-drop things
        suite.addTestSuite(TestSearchMenu.class);

        // thisone is for parsing wiki-tables
        suite.addTestSuite(TestWikiTableVisitor.class);

        return suite;
    }*/
}
