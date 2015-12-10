package qube.qoan.gui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.ojalgo.random.Normal;
import org.ojalgo.random.RandomNumber;
import org.vaadin.addon.JFreeChartWrapper;
import org.vaadin.visjs.networkDiagram.Edge;
import org.vaadin.visjs.networkDiagram.NetworkDiagram;
import org.vaadin.visjs.networkDiagram.Node;
import org.vaadin.visjs.networkDiagram.options.Options;
import qube.qoan.gui.components.QoanHeader;

/**
 * Created by rainbird on 10/29/15.
 */
public class ComponentsView extends VerticalLayout implements View {

    public static String NAME = "components";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        UI.getCurrent().getPage().setTitle("Qoan Components Display");

        QoanHeader header = new QoanHeader();
        addComponent(header);

        Component graph = createNetworkDiagram();
        graph.setHeight("400px");
        graph.setWidth("400px");
        addComponent(graph);

        Component chart = createChart();
        addComponent(chart);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Label label = new Label("Thank you for clicking");
                addComponent(label);
            }
        });

        addComponent(button);
    }

    // methods are for chart creation coming from the examples
    private Component createChart() {

        // try our hand with an histogram
        int number = 1000;
        double[] value = new double[number];
        //Random generator = new Random();
        RandomNumber generator = new Normal(0.5, 0.1);
        //RandomNumber generator = new Weibull(1.0, 1.5);
        //RandomNumber generator = new Poisson(15);
        for (int i=1; i < number; i++) {
            value[i] = generator.doubleValue();
        }

        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("Histogram", value, 50);
        String plotTitle = "Histogram";
        String xaxis = "value";
        String yaxis = "frequency";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = false; // show legends- if there is only one series not necessary
        boolean toolTips = false;
        boolean urls = false;
        JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis,
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
        Node node1 = new Node(1,"vienna");
        Node node2 = new Node(2,"bergen");
        Node node3 = new Node(3,"paris");
        Node node4 = new Node(4,"london");
        Node node5 = new Node(5,"helsinki");
        Node node6 = new Node(6,"timbuktu");

        //create edges
        Edge edge1 = new Edge(node1.getId(),node2.getId());
        Edge edge2 = new Edge(node1.getId(),node3.getId());
        Edge edge3 = new Edge(node2.getId(),node5.getId());
        Edge edge4 = new Edge(node2.getId(),node4.getId());
        Edge edge5 = new Edge(node6.getId(),node4.getId());
        Edge edge6 = new Edge(node1.getId(),node5.getId());
        Edge edge7 = new Edge(node1.getId(),node4.getId());

        networkDiagram.addNode(node1);
        networkDiagram.addNode(node2,node3,node4,node5,node6);
        networkDiagram.addEdge(edge1,edge2,edge3,edge4, edge5, edge6, edge7);

        return networkDiagram;
    }
}
