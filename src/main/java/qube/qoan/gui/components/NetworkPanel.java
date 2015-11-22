package qube.qoan.gui.components;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;
import qube.qai.network.QaiNetwork;

import java.util.Collection;

/**
 * Created by rainbird on 11/21/15.
 * for displaying QaiNetworks on screen
 */
public class NetworkPanel extends Panel {

    private QaiNetwork network;

    private NetworkDiagram diagram;

    public NetworkPanel() {
        super();
    }

    public NetworkPanel(QaiNetwork network) {
        this();
        this.network = network;

        initialize(network);
    }

    private void initialize(QaiNetwork network) {

        Options options = new Options();
        options.setWidth("600px");
        options.setHeight("350px");

        diagram = new NetworkDiagram(options);
        diagram.setSizeFull();

        Collection<QaiNetwork.Vertex> vertices = network.getVertices();
        int count = 0;
        for (QaiNetwork.Vertex vertex : vertices) {
            Node node = new Node(count, vertex.getName());
            vertex.setDiagramId(count);
            diagram.addNode(node);
            // increment the id-count
            count++;
        }

        Collection<QaiNetwork.Edge> edges = network.getAllEdges();
        for (QaiNetwork.Edge edge : edges) {
            Edge diagramEdge = new Edge(edge.getFrom().getDiagramId(), edge.getTo().getDiagramId());
            diagram.addEdge(diagramEdge);
        }

        setContent(diagram);
    }
}
