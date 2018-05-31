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

package qube.qoan.gui.components.workspace.network;

import com.vaadin.ui.Panel;
import org.qai.network.Network;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;

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

        Collection<Network.Edge> edges = network.getEdges();
        for (Network.Edge edge : edges) {
            Edge diagramEdge = new Edge(edge.getFrom().getDiagramId(), edge.getTo().getDiagramId());
            diagram.addEdge(diagramEdge);
        }

        setContent(diagram);
    }
}
