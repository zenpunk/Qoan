package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.ui.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.vaadin.addon.JFreeChartWrapper;
import qube.qai.data.Arguments;
import qube.qai.data.Metrics;
import qube.qai.data.Selector;
import qube.qai.data.TimeSequence;
import qube.qai.data.analysis.Statistics;
import qube.qai.matrix.Matrix;
import qube.qai.network.Network;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.ProcedureChain;
import qube.qoan.gui.components.common.InnerPanel;
import qube.qoan.gui.components.common.MetricsPanel;
import qube.qoan.gui.components.workspace.network.NetworkPanel;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by rainbird on 12/5/15.
 */
public class ProcedureTag extends Panel {

    private Procedure procedure;
    private Layout parentLayout;

    private int top = 100;
    private int left = 100;

    public ProcedureTag(Procedure procedure, Layout parentLayout) {
        this.procedure = procedure;
        this.parentLayout = parentLayout;

        initialize(procedure);
    }

    private void initialize(Procedure procedure) {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("200px");

        String name = procedure.getName();

        Label nameLabel = new Label(name);
        nameLabel.setStyleName("bold");
        layout.addComponent(nameLabel);

        if (procedure.hasExecuted()) {
            String uuid = "[" + procedure.getUuid() + "]";
            Label uuidLabel = new Label(uuid);
            uuidLabel.setStyleName("bold");
            layout.addComponent(uuidLabel);
        }
//        Label descriptionLabel = new Label(procedure.getDescription());
//        layout.addComponent(descriptionLabel);

        // add the buttons and events which go with them
        try {
            HorizontalLayout buttonLayout = new HorizontalLayout();
            Button openButton = new Button("open");
            Method onClickOpen = this.getClass().getMethod("onClickOpen", Button.ClickEvent.class);
            openButton.addListener(Button.ClickEvent.class, this, onClickOpen);
            openButton.setStyleName("link");
            buttonLayout.addComponent(openButton);

            Button removeButton = new Button("remove");
            Method onClickRemove = this.getClass().getMethod("onClickRemove", Button.ClickEvent.class);
            removeButton.addListener(Button.ClickEvent.class, this, onClickRemove);
            removeButton.setStyleName("link");
            buttonLayout.addComponent(removeButton);
            layout.addComponent(buttonLayout);

        } catch (NoSuchMethodException e) {
            Notification.show("NoSuchMethodException: " + e.getMessage());
        }

        setContent(layout);
    }

    /**
     * event handler for removing the tag from desktop
     * @param event
     */
    public void onClickRemove(Button.ClickEvent event) {
        // we are removing the toDecorate- which is the d&d-wrapper
        Component parent = getParent();
        parentLayout.removeComponent(parent);
    }

    /**
     * event handler for opening ProcedureTags
     * @param event
     */
    public void onClickOpen(Button.ClickEvent event) {

        String title = procedure.getName();
        Panel windowContent = new Panel();
        VerticalLayout layout = new VerticalLayout();
        // if procedure has already executed, then add the views to results as well
        if (procedure.hasExecuted()) {
            // create instead a tab-sheet
            TabSheet tabSheet = new TabSheet();

            // in the first panel we have the procedure description and parameters
            Panel firstPanel = new Panel();
            VerticalLayout panelLayout = new VerticalLayout();
            Label description = new Label(procedure.getDescription());
            panelLayout.addComponent(description);
            MetricsPanel metricsPanel = new MetricsPanel("Parameters:", procedure.getArguments());
            panelLayout.addComponent(metricsPanel);
            firstPanel.setContent(panelLayout);

            tabSheet.addTab(firstPanel).setCaption("Procedure:");
            addDisplayOfResults(tabSheet, procedure.getArguments());

            layout.addComponent(tabSheet);
        } else {
            // if procedure has not yet executed don't bother with display of results in tabs
            Label description = new Label(procedure.getDescription());
            layout.addComponent(description);
            MetricsPanel metricsPanel = new MetricsPanel("Parameters:", procedure.getArguments());
            layout.addComponent(metricsPanel);
        }

        windowContent.setContent(layout);
        InnerPanel window = new InnerPanel(title, windowContent);
        // if toDecorate is an absolute layout, we need a position to add the thing as well
        if (parentLayout instanceof AbsoluteLayout) {
            left = left + 5;
            top = top + 5;
            String positionString = "left: " + left + "px; top: " + top + "px;";
            DragAndDropWrapper dndWrapper = new DragAndDropWrapper(window);
            dndWrapper.setSizeUndefined();
            dndWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
            ((AbsoluteLayout)parentLayout).addComponent(dndWrapper, positionString);
        } else {
            parentLayout.addComponent(window);
        }
    }

    private void addDisplayOfResults(TabSheet tabSheet, Arguments arguments) {

        // if we have these things, we add a metric for them, to begin with
        if (arguments.getResultNames().contains(ProcedureChain.AVERAGE_TIME_SEQUENCE)
                && arguments.getResultNames().contains(ProcedureChain.SORTED_ITEMS)) {

            Map<String, Statistics> sortedTimeSequences = (Map<String, Statistics>) arguments.getResult(ProcedureChain.SORTED_ITEMS);
            TimeSequence<Double> averageTimeSequence = (TimeSequence<Double>) arguments.getResult(ProcedureChain.AVERAGE_TIME_SEQUENCE);
            // say, two best, two worst and average
            String[] names = collectRankedNames(sortedTimeSequences);
            Map<String, Selector> timeSeriesMap = (Map<String, Selector>) arguments.getSelector(ProcedureChain.FROM).getData();
            Component chart = createChartsFor(timeSeriesMap, names);
            tabSheet.addTab(chart).setCaption("Selected time-series");

        } else if (arguments.getResultNames().contains(ProcedureChain.TIME_SEQUENCE_METRICS)) {

            String name = ProcedureChain.TIME_SEQUENCE_METRICS;
            TimeSequence timeSequence = (TimeSequence) arguments.getSelector(ProcedureChain.INPUT_TIME_SEQUENCE).getData();
            Component chart = createChartFor(timeSequence);

            addMetricsToTabSheet(name, tabSheet, arguments, chart);

        } else if (arguments.getResultNames().contains(ProcedureChain.NETWORK_METRICS)) {

            String name = ProcedureChain.NETWORK_METRICS;
            addMetricsToTabSheet(name, tabSheet, arguments, null);
            Network network = (Network) arguments.getSelector(ProcedureChain.INPUT_NETWORK).getData();
            NetworkPanel networkPanel = new NetworkPanel(network);
            tabSheet.addTab(networkPanel).setCaption("Graph");

        } else if (arguments.getResultNames().contains(ProcedureChain.MATRIX_METRICS)
                || arguments.getResultNames().contains(ProcedureChain.MATRIX_DATA_METRICS)) {

            String name = ProcedureChain.MATRIX_METRICS;
            Matrix matrix = (Matrix) arguments.getSelector(ProcedureChain.INPUT_MATRIX).getData();
            Component chart = createHistogramFor(matrix.toArray());
            addMetricsToTabSheet(name, tabSheet, arguments, chart);
            addMetricsToTabSheet(ProcedureChain.MATRIX_DATA_METRICS, tabSheet, arguments, null);

        } /*else if (arguments.getResultNames().contains(ProcedureChain.MATRIX_DATA_METRICS)) {

            String name = ProcedureChain.MATRIX_DATA_METRICS;

        } else if (arguments.getResultNames().contains(ProcedureChain.MAP_OF_TIME_SEQUENCE)) {
        }*/

    }

    /**
     * returns first, second, one-from-last and last element names in collection
     * @param sortedTimeSequence
     * @return
     */
    private String[] collectRankedNames(Map<String, Statistics> sortedTimeSequence) {
        String[] names = new String[4];
        int count = 0;
        int max = sortedTimeSequence.size();
        for (String name : sortedTimeSequence.keySet()) {
            if (count == 0) {
                names[0] = name;
            }
            if (count == 1) {
                names[1] = name;
            }
            if (count == max-2) {
                names[2] = name;
            }
            if (count== max-1) {
                names[3] = name;
            }
            count++;
        }

        return names;
    }

    private void addMetricsToTabSheet(String name, TabSheet tabSheet, Arguments arguments, Component chart) {

        Metrics metrics = (Metrics) arguments.getResult(name);
        Panel panel = new Panel();
        if (chart != null) {
            HorizontalLayout layout = new HorizontalLayout();
            MetricsPanel metricsPanel = new MetricsPanel(name, metrics);
            layout.addComponent(metricsPanel);
            layout.addComponent(chart);
            panel.setContent(layout);
        } else {
            MetricsPanel metricsPanel = new MetricsPanel(name, metrics);
            panel.setContent(metricsPanel);
        }

        tabSheet.addTab(panel).setCaption(name);
    }

    private Component createHistogramFor(double[] values) {

        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("Histogram", values, 50);
        String plotTitle = "Histogram";
        String xaxis = "value";
        String yaxis = "frequency";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = false; // show legends- if there is only one series not necessary
        boolean toolTips = false;
        boolean urls = false;
        JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis,
                dataset, orientation, show, toolTips, urls);

        return createChart(chart);
    }

    private Component createChart(final JFreeChart chart) {
        return new JFreeChartWrapper(chart) {

            @Override
            public void attach() {
                super.attach();
                setResource("src", getSource());
            }
        };
    }

    private Component createChartFor(TimeSequence timeSequence) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Date[] dates = timeSequence.toDates();
        Number[] values = timeSequence.toArray();

        TimeSeries chartSeries = new TimeSeries("Time-sequence");
        for (int i = 0; i < values.length; i++) {
            Day day = new Day(dates[i]);
            chartSeries.add(day, values[i].doubleValue());
        }

        return createChart(dataset);
    }

    /**
     * if the procedure was sorting of time-series
     * the first two, average and last two time-series are displayed
     * @param timeSequenceMap
     * @param names
     * @return
     */
    private Component createChartsFor(Map<String, Selector> timeSequenceMap, String... names) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for (String name : names) {
            TimeSequence timeSequence = (TimeSequence) timeSequenceMap.get(name).getData();
            TimeSeries chartSeries = new TimeSeries(name);
            Day day = new Day(01, 01, 2015);
            for (Iterator<Number> it = timeSequence.iterator(); it.hasNext(); ) {
                Number item = it.next();
                chartSeries.add(day, item);
                day = (Day) day.next();
            }
            dataset.addSeries(chartSeries);
        }

        return createChart(dataset);
    }

    private Component createChart(TimeSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Selected time-series",
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
}
