package qube.qoan;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.TestCase;
import qube.qoan.services.QoanTestModule;

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
}
