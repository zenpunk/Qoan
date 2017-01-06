package qube.qoan.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import qube.qoan.gui.components.TestSearchMenu;
import qube.qoan.gui.components.workspace.finance.parser.TestWikiArticleIntegration;

/**
 * Created by rainbird on 11/19/15.
 */
public class QoanTestBase extends TestCase {

    protected Injector injector;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // @TODO QaiModule is not right here... find a way to make the QaiTestModule to be accessible
        injector = Guice.createInjector(new QoanTestModule());
        injector.injectMembers(this);

    }

}
