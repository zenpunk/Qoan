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

package qube.qoan.server;


import com.google.inject.Injector;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;


/**
 * Created by rainbird on 10/26/15.
 */
//@WebServlet(value = "/*", asyncSupported = true)
//@VaadinServletConfiguration(productionMode = false, ui = QoanUI.class)
public class WebServer {

    private static String contextPath = "/";
    //private static String resourceBase = "./webapp";
    private static String resourceBase = "./target/classes/webapp";
    private static int httpPort = 8080;

    private static Injector injector;

    /**
     * main function, starts the jetty server.
     *
     * @param args
     */
    public static void main(String[] args) {

        Server server = new Server(httpPort);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(contextPath);
        webAppContext.setResourceBase(resourceBase);
        webAppContext.setConfigurations(new Configuration[]{
                new AnnotationConfiguration(),
                new WebXmlConfiguration(),
                new WebInfConfiguration(),
                //new TagLibConfiguration(),
                //new PlusConfiguration(),
                //new MetaInfConfiguration(),
                //new FragmentConfiguration(),
                new EnvConfiguration()});
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        //webAppContext.(new EnvironmentLoader());
        webAppContext.addEventListener(new EnvironmentLoaderListener());

        server.setHandler(webAppContext);

        System.out.println("Go to http://localhost:" + httpPort + contextPath);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Injector getInjector() {
        return injector;
    }
}
