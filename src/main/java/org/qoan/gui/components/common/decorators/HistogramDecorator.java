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

package org.qoan.gui.components.common.decorators;

import com.vaadin.pekka.resizablecsslayout.ResizableCssLayout;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.qai.persistence.QaiDataProvider;
import org.qai.persistence.StockEntity;
import org.qai.persistence.StockQuote;
import org.qai.services.implementation.SearchResult;
import org.vaadin.addon.JFreeChartWrapper;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by rainbird on 7/8/17.
 */
public class HistogramDecorator extends BaseDecorator {

    private Image iconImage;

    private String name = "Adj.Close Dist.";

    @Inject
    private QaiDataProvider<StockEntity> dataProvider;

    private ResizableCssLayout imageWrapper;

    public HistogramDecorator() {
        iconImage = new Image(name,
                new ClassResource("gui/images/chart-icon.png"));
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        StockEntity entity = dataProvider.brokerSearchResult(toDecorate);
        if (entity == null || entity.getQuotes() == null || entity.getQuotes().isEmpty()) {
            return;
        }

        Set<StockQuote> quotes = entity.getQuotes();
        int number = quotes.size();
        double[] value = new double[number];
        int index = 0;
        for (StockQuote quote : quotes) {
            value[index] = quote.getAdjustedClose();
            index++;
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

        Component component = new JFreeChartWrapper(chart) {

            @Override
            public void attach() {
                super.attach();
                setResource("src", getSource());
            }
        };

        setContent(component);
    }

}
