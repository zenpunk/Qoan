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

package qube.qoan.server;


import com.google.inject.Injector;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by rainbird on 10/26/15.
 */
public class WebServer {

    private static Logger logger = LoggerFactory.getLogger("WebServer");

    private static String contextPath = "/";
    //private static String resourceBase = "./webapp";
    private static String resourceBase = "./target/classes/webapp";
    private static int httpPort = 8080;
    private static String wikiUrl = "192.168.0.206:/8081";
    private static Injector injector;

    /**
     * main function, starts the jetty server.
     *
     * @param args
     */
    public static void main(String[] args) {

        Server server = new Server(httpPort);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        webAppContext.setContextPath(contextPath);
        webAppContext.setResourceBase(resourceBase);
        webAppContext.setConfigurations(new Configuration[]{
                new AnnotationConfiguration(),
                new WebXmlConfiguration(),
                new WebInfConfiguration(),
                //new TagLibConfiguration(),
                new PlusConfiguration(),
                new MetaInfConfiguration(),
                //new FragmentConfiguration(),
                new EnvConfiguration()});
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        //webAppContext.(new EnvironmentLoader());
        webAppContext.addEventListener(new EnvironmentLoaderListener());

        // @TODO out of some reason the redirect rules are getting ignored....
//        RewriteHandler rewrite = new RewriteHandler();
//        rewrite.setRewriteRequestURI(true);
//        rewrite.setRewritePathInfo(true);
//        rewrite.setOriginalPathAttribute("requestedPath");
//
//        RedirectPatternRule redirect = new RedirectPatternRule();
//        redirect.setPattern("wiki.qoan.org");
//        redirect.setLocation("192.168.0.206:/8081");
//        redirect.setTerminating(true);
//        rewrite.addRule(redirect);

        //server.setHandler(webAppContext);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{webAppContext, new DefaultHandler()});
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
            logger.info("Go to http://localhost:" + httpPort + contextPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Injector getInjector() {
        return injector;
    }
}
