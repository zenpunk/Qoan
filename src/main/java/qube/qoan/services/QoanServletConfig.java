package qube.qoan.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import qube.qai.main.QaiModule;
import qube.qoan.QoanUI;

/**
 * Created by rainbird on 11/26/15.
 */
public class QoanServletConfig extends GuiceServletContextListener {

    private boolean debug = true;

    @Override
    protected Injector getInjector() {

        ServletModule module = new ServletModule() {
            @Override
            protected void configureServlets() {
                log("Starting to filter servlets...");
                serve("/*").with(VaadinServlet.class);

                bind(UI.class).to(QoanUI.class).in(ServletScopes.SESSION);

            }
        };

        Injector injector = Guice.createInjector(module); // , new QoanModule(), new QaiModule()

        return injector;
    }

    private void log(String message) {
        if (debug) {
            System.out.println(message);
        }
    }
}
