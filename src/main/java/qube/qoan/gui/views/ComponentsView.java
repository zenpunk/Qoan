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

package qube.qoan.gui.views;

import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.ojalgo.random.Normal;
import org.ojalgo.random.RandomNumber;
import org.vaadin.addon.JFreeChartWrapper;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;
import pl.pdfviewer.PdfViewer;
import qube.qoan.gui.components.common.NglAdapter;

import java.io.File;

/**
 * Created by rainbird on 10/29/15.
 */
public class ComponentsView extends QoanView {

    public static String NAME = "components";

    public ComponentsView() {
        this.viewTitle = "Qoan Components";
    }

    private String jsExec = "NGL.init( onInit );";

    private String jsExecParam = "NGL.init( onInitLoad('";

    protected void initialize() {

        Layout layout = new VerticalLayout();

        Label welcomeLabel = new Label("Welcome to the demo page for Qoan components.");
        layout.addComponent(welcomeLabel);

        NglAdapter nglViewer = new NglAdapter();
        layout.addComponent(nglViewer);

        HorizontalLayout selectLine = new HorizontalLayout();

        TextField nameField = new TextField("Molecule name");
        nameField.setValue("1crm");
        selectLine.addComponent(nameField);

        Button showNgl = new Button("Show molecule");
        showNgl.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String moleculeName = nameField.getValue();
                if (StringUtils.isEmpty(moleculeName)) {
                    JavaScript.getCurrent().execute(jsExec);
                } else {
                    String toExecute = jsExecParam + moleculeName + "' ))";
                    //JavaScript.getCurrent().execute("alert('" + toExecute + "')");
                    JavaScript.getCurrent().execute(toExecute);
                }
            }
        });
        selectLine.addComponent(showNgl);
        layout.addComponent(selectLine);
//
//        MyComponent mycomponent = new MyComponent();
//        layout.addComponent(mycomponent);
//
//        Button showNglButton = new Button("Show NGL-Viewer");
//        showNglButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent clickEvent) {
//                Window window = new Window("A Window");
//                VerticalLayout content = new VerticalLayout();
//                content.setMargin(true);
//
//                NglAdapter nglViewer = new NglAdapter("");
//                content.addComponent(nglViewer);
//
//                window.setContent(content);
//                window.setWidth("800px");
//                UI.getCurrent().addWindow(window);
//            }
//        });
//        layout.addComponent(showNglButton);

        Component timeSeries = createTimeSeries();
        layout.addComponent(timeSeries);

        Component histogram = createHistogram();
        layout.addComponent(histogram);

        Button showPdfViewerButton = new Button("Show Pdf-Viewer");
        showPdfViewerButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window window = new Window("A Window");

                File file = new File("/home/rainbird/projects/work/docs/powerpoint/Qoan.pdf");
                PdfViewer pdfViewer = new PdfViewer(file);
                pdfViewer.setSizeFull();
                pdfViewer.setVisible(true);
                pdfViewer.setPage(1);

                window.setContent(pdfViewer);
                window.setWidth("800px");
                UI.getCurrent().addWindow(window);
            }
        });
        layout.addComponent(showPdfViewerButton);

        Button showGraphButton = new Button("Open Window for example Graph");
        showGraphButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Window window = new Window("A Window");
                VerticalLayout content = new VerticalLayout();
                content.setMargin(true);
                //Label label = new Label("this is some test text to display in the window");
                //content.addComponent(label);
                Label chartLabel = new Label("A small graph");
                content.addComponent(chartLabel);
                Component graph = createNetworkDiagram();
                graph.setHeight("600px");
                graph.setWidth("800px");
                content.addComponent(graph);

                window.setContent(content);
                UI.getCurrent().addWindow(window);
            }
        });
        layout.addComponent(showGraphButton);

        addComponent(layout);
    }

    private Component createTimeSeries() {
        XYDataset dataset = createDataset("microsoft", "apple", "google", "oracle");
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Computing Test",
                "Days",
                "Value",
                dataset,
                true,
                false,
                false);
        return new JFreeChartWrapper(chart) {

            @Override
            public void attach() {
                super.attach();
                setResource("src", getSource());
            }
        };
    }

    private XYDataset createDataset(String... names) {
        int size = 50;
        RandomNumber generator = new Normal(0.5, 10.0);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        int day = 01;
        int month = 01;
        int year = 2000;
        for (String name : names) {
            TimeSeries firefox = new TimeSeries(name);
            Day current = new Day(day, month, year);
            for (int i = 0; i < size; i++) {
                firefox.add(current, generator.doubleValue());
                current = (Day) current.next();
            }
            dataset.addSeries(firefox);
        }

        return dataset;
    }

    // methods are for chart creation coming from the examples
    private Component createHistogram() {

        // try our hand with an histogram
        int number = 10000;
        double[] value = new double[number];
        //Random generator = new Random();
        RandomNumber generator = new Normal(0.5, 10.0);
        //RandomNumber generator = new Weibull(1.0, 1.5);
        //RandomNumber generator = new Poisson(15);
        for (int i = 1; i < number; i++) {
            value[i] = generator.doubleValue();
        }

        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("Histogram", value, 100);
        String plotTitle = "Histogram";
        String xaxis = "value";
        String yaxis = "frequency";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = false; // show legends- if there is only one series not necessary
        boolean toolTips = false;
        boolean urls = false;
        JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis,
                dataset, orientation, show, toolTips, urls);

        return new JFreeChartWrapper(chart) {

            @Override
            public void attach() {
                super.attach();
                setResource("src", getSource());
            }
        };
    }

    // another go with the graph library
    // this one using the library visjs-addon
    public NetworkDiagram createNetworkDiagram() {

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

        return networkDiagram;
    }
}
