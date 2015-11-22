package qube.qoan.server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


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
        //webAppContext.setWar(resourceBase);
        webAppContext.setResourceBase(resourceBase);
        webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webAppContext);

        // another trial- this time we'll try to add the servlet directly
//        ServletHandler handler = new ServletHandler();
//        server.setHandler(handler);
//        handler.addServletWithMapping(VaadinServlet.class, "/*");

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
