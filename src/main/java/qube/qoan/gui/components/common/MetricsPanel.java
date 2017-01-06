package qube.qoan.gui.components.common;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qai.data.MetricTyped;
import qube.qai.data.Metrics;

/**
 * Created by rainbird on 12/4/15.
 */
public class MetricsPanel extends Panel {

    /**
     * displays the contents of MetricTyped data it is given
     */
    public MetricsPanel(String name, MetricTyped metricTyped) {
        super();

        initialize(name, metricTyped);

    }

    private void initialize(String name, MetricTyped metricTyped) {

        VerticalLayout layout = new VerticalLayout();

        Label nameLabel = new Label(name);
        nameLabel.setStyleName("bold");
        layout.addComponent(nameLabel);

        Metrics metrics = metricTyped.buildMetrics();
        for (String p : metrics.getNames()) {
            HorizontalLayout rowLayout = new HorizontalLayout();
            String pString = p + ": ";
            Label pLabel = new Label(pString);
            rowLayout.addComponent(pLabel);
            String valueString = metrics.getValue(p).toString();
            Label valueLabel = new Label(valueString);
            rowLayout.addComponent(valueLabel);
            layout.addComponent(rowLayout);
        }

        setContent(layout);
    }


}
