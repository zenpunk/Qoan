package qube.qoan.gui.components.workspace;

import com.vaadin.ui.Panel;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;
import qube.qai.network.Network;

import java.util.Collection;

/**
 * Created by rainbird on 11/21/15.
 * for displaying QaiNetworks on screen
 */
public class NetworkPanel extends Panel {

    private Network network;

    private NetworkDiagram diagram;

    public NetworkPanel() {
        super();
    }

    public NetworkPanel(Network network) {
        this();
        this.network = network;

        initialize(network);
    }

    private void initialize(Network network) {

        Options options = new Options();
        options.setWidth("600px");
        options.setHeight("350px");

        diagram = new NetworkDiagram(options);
        diagram.setSizeFull();

        Collection<Network.Vertex> vertices = network.getVertices();
        int count = 0;
        for (Network.Vertex vertex : vertices) {
            Node node = new Node(count, vertex.getName());
            vertex.setDiagramId(count);
            diagram.addNode(node);
            // increment the id-count
            count++;
        }

        Collection<Network.Edge> edges = network.getAllEdges();
        for (Network.Edge edge : edges) {
            Edge diagramEdge = new Edge(edge.getFrom().getDiagramId(), edge.getTo().getDiagramId());
            diagram.addEdge(diagramEdge);
        }

        setContent(diagram);
    }
}
