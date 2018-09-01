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

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.qai.persistence.QaiDataProvider;
import org.qai.persistence.StockEntity;
import org.qai.persistence.StockQuote;
import org.qai.services.implementation.SearchResult;
import org.vaadin.addon.JFreeChartWrapper;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by rainbird on 7/4/17.
 */
public class StockQuotesDecorator extends BaseDecorator {



    @Inject
    private QaiDataProvider<StockEntity> dataProvider;

    private Image iconImage;

    private String name = "Stock Quotes";

    public StockQuotesDecorator() {
        iconImage = new Image(name,
                new ClassResource("gui/images/chart-icon.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        Component wrapper = new Panel();
        StockEntity entity = dataProvider.brokerSearchResult(toDecorate);
        Set<StockQuote> quotes = entity.getQuotes();
        if (quotes != null && !quotes.isEmpty()) {

            XYDataset dataset = createDataSet(toDecorate.getTitle(), quotes);
            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    toDecorate.getTitle(),
                    "Days",
                    "Close",
                    dataset,
                    true,
                    false,
                    false);
            wrapper = new JFreeChartWrapper(chart) {

                @Override
                public void attach() {
                    super.attach();
                    setResource("src", getSource());
                }
            };
        }

        setContent(wrapper);
    }

    private XYDataset createDataSet(String title, Set<StockQuote> quotes) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries series = new TimeSeries(title);
        for (StockQuote quote : quotes) {
            Day date = new Day(quote.getQuoteDate());
            series.add(date, quote.getClose());
        }

        dataset.addSeries(series);
        return dataset;
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }

    @Override
    public String getName() {
        return name;
    }
}