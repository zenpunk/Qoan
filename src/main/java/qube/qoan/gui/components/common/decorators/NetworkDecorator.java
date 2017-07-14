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

package qube.qoan.gui.components.common.decorators;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;
import qube.qai.services.implementation.SearchResult;

/**
 * Created by rainbird on 7/7/17.
 */
public class NetworkDecorator extends Panel implements Decorator {

    private Image iconImage;

    @Override
    public void decorate(SearchResult toDecorate) {
        iconImage = new Image("Semantic network",
                new ClassResource("qube/qoan/images/network.jpeg"));
    }

    @Override
    public void addDecorator(String name, Decorator decorator) {
        Options options = new Options();
        NetworkDiagram networkDiagram = new NetworkDiagram(options);
        //networkDiagram.setSizeFull();
        //crete nodes
        Node node1 = new Node(1, "vienna");
        Node node2 = new Node(2, "bergen");
        Node node3 = new Node(3, "paris");
        Node node4 = new Node(4, "london");
        Node node5 = new Node(5, "helsinki");
        Node node6 = new Node(6, "timbuktu");

        //create edges
        Edge edge1 = new Edge(node1.getId(), node2.getId());
        Edge edge2 = new Edge(node1.getId(), node3.getId());
        Edge edge3 = new Edge(node2.getId(), node5.getId());
        Edge edge4 = new Edge(node2.getId(), node4.getId());
        Edge edge5 = new Edge(node6.getId(), node4.getId());
        Edge edge6 = new Edge(node1.getId(), node5.getId());

        networkDiagram.addNode(node1);
        networkDiagram.addNode(node2, node3, node4, node5, node6);
        networkDiagram.addEdge(edge1, edge2, edge3, edge4, edge5, edge6);
        setContent(networkDiagram);
    }

    @Override
    public void decorateAll(SearchResult searchResult) {

    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }
}
