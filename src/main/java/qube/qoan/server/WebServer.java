package qube.qoan.server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import qube.qoan.services.QoanServletConfig;


/**
 * Created by rainbird on 10/26/15.
 */
public class WebServer {

    private static String contextPath = "/";
    private static String resourceBase = "target/webapp";
    private static int httpPort = 8080;

    /**
     * main function, starts the jetty server.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        Server server = new Server(httpPort);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(contextPath);
        webAppContext.setResourceBase(resourceBase);
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        //webAppContext.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        webAppContext.addEventListener(new QoanServletConfig());
        server.setHandler(webAppContext);

        System.out.println("Go to http://localhost:" + httpPort + contextPath);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // now the web-server has been started, we invoke an instance of Hazelcast as well
        //HazelcastInstance hazelcastInstance = Hazelcast.getInstance();

    }
}
