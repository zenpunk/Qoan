package qube.qoan.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import qube.qoan.gui.components.TestSearchMenu;

/**
 * Created by rainbird on 11/19/15.
 */
public class QoanTestBase extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Injector injector = Guice.createInjector(new QoanTestModule());
        injector.injectMembers(this);


    }


    public static void main(String[] params) {
        String[] tests = {QoanTestBase.class.getName()};
        TestRunner.main(tests);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("All tests");

        // persistence.mapstores
        suite.addTestSuite(TestSearchMenu.class);



        return suite;
    }
}
