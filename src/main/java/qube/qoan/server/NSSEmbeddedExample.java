package qube.qoan.server;

import com.bigdata.rdf.sail.webapp.NanoSparqlServer;
import org.apache.log4j.Logger;


/**
 * Class demonstrates how to start the {@link NanoSparqlServer} from within
 * embedded code.
 *
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 */
public class NSSEmbeddedExample implements Runnable {

    /**
     * -Dlog4j.configuration=${workspace_loc:bigdata-jar}/src/main/resources/log4j.properties
     * -Djetty.home=${workspace_loc:bigdata-war-html}/src/main/webapp/
     * -Djetty.resourceBase=${workspace_loc:bigdata-war-html}/src/main/webapp/
     * -DjettyXml=${workspace_loc:bigdata-jar}/src/main/webapp/WEB-INF/jetty.xml
     * -Djetty.overrideWebXml=${workspace_loc:bigdata-war-html}/src/main/webapp/WEB-INF/override-web.xml
     */

    private static final Logger log = Logger.getLogger(NSSEmbeddedExample.class);

    //    private int port;
//    private final IIndexManager indexManager;
//    private final Map<String, String> initParams;
//
//    /**
//     * @param port The desired port -or- ZERO (0) to use a random open port.
//     */
//    public NSSEmbeddedExample(final int port, final IIndexManager indexManager,
//                              final Map<String, String> initParams) {
//
//        if (indexManager == null)
//            throw new IllegalArgumentException();
//
//        if (initParams == null)
//            throw new IllegalArgumentException();
//
//        this.port = port;
//        this.indexManager = indexManager;
//        this.initParams = initParams;
//
//    }
//
//    @Override
    public void run() {
//
//        String jettyXml = System.getProperty(NanoSparqlServer.SystemProperties.JETTY_XML, "jetty.xml");
//        System.setProperty("jetty.home", jettyXml.getClass().getResource("/war").toExternalForm());
//
//
//        Server server = null;
//        try {
//
//            server = NanoSparqlServer.newInstance(port, indexManager,
//                    initParams);
//
//            NanoSparqlServer.awaitServerStart(server);
//
//            // Block and wait. The NSS is running.
//            server.join();
//
//        } catch (Throwable t) {
//
//            log.error(t, t);
//
//        } finally {
//
//            if (server != null) {
//
//                try {
//
//                    server.stop();
//
//                } catch (Exception e) {
//
//                    log.error(e, e);
//
//                }
//
//                server = null;
//
//                System.out.println("Halted.");
//
//            }
//
//        }
//
    }
//
//    /**
//     * Start and run an {@link NanoSparqlServer} instance from embedded code.
//     *
//     * @param args ignored.
//     * @throws Exception
//     */
//    public static void main(final String[] args) throws Exception {
//
//        final int port = 9090; // random port.
//
//        /*
//         * Create or re-open a durable database instance using default
//         * configuration properties. There are other constructors that allow you
//         * to take more control over this process.
//         */
//        final BigdataSail sail = new BigdataSail();
//
//        sail.initialize();
//
//        try {
//
//            final IIndexManager indexManager = sail.getDatabase()
//                    .getIndexManager();
//
//            final Map<String, String> initParams = new LinkedHashMap<String, String>();
//
//            new Thread(new NSSEmbeddedExample(port, indexManager, initParams))
//                    .run();
//
//        } finally {
//
//            sail.shutDown();
//
//        }
//
//    }
}
