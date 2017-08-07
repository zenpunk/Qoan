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
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.vaadin.addon.JFreeChartWrapper;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.StockQuote;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

import static qube.qai.main.QaiConstants.STOCK_QUOTES;

/**
 * Created by rainbird on 7/4/17.
 */
public class StockQuotesDecorator extends BaseDecorator {

    @Inject
    @Named("Stock_Quotes")
    private SearchServiceInterface stocksSearch;

    @Inject
    private QaiDataProvider<StockQuote> stockQuoteProvider;

    private Image iconImage;

    public StockQuotesDecorator() {
        iconImage = new Image("Stock Quotes",
                new ClassResource("gui/images/stocks-index.png"));
    }

    @Override
    public void decorate(SearchResult toDecorate) {

        Collection<SearchResult> results = stocksSearch.searchInputString(toDecorate.getDescription(), STOCK_QUOTES, 0);

        // don't bother if there are no results
        if (results == null || results.isEmpty()) {
            return;
        }

        XYDataset dataset = createDataSet(toDecorate.getTitle(), results);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                toDecorate.getTitle() + " Quotes",
                "Days",
                "Adjusted Close",
                dataset,
                true,
                false,
                false);
        Component wrapper = new JFreeChartWrapper(chart) {

            @Override
            public void attach() {
                super.attach();
                setResource("src", getSource());
            }
        };

        setContent(wrapper);
    }

    private XYDataset createDataSet(String title, Collection<SearchResult> results) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries series = new TimeSeries(title);
        for (SearchResult result : results) {
            StockQuote quote = stockQuoteProvider.brokerSearchResult(result);
            Day date = new Day(quote.getQuoteDate());
            series.add(date, quote.getAdjustedClose());
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
        return "Stock Quotes";
    }
}
