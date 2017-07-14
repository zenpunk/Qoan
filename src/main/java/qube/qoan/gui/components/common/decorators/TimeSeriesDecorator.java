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
import com.vaadin.ui.Panel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.vaadin.addon.JFreeChartWrapper;
import qube.qai.main.QaiConstants;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Created by rainbird on 7/4/17.
 */
public class TimeSeriesDecorator extends Panel implements Decorator, QaiConstants {

    @Inject
    @Named("Stock_Quotes")
    private SearchServiceInterface stocksSearch;

    private Image iconImage;

    public TimeSeriesDecorator() {
        iconImage = new Image("Stock Quotes",
                new ClassResource("qube/qoan/images/stocks-index.png"));
    }

    @Override
    public void addDecorator(String name, Decorator decorator) {

    }

    @Override
    public void decorate(SearchResult toDecorate) {

        Collection<SearchResult> results = stocksSearch.searchInputString(STOCK_QUOTES, toDecorate.getTitle(), 1000);

        XYDataset dataset = createDataSet(results);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Computing Test",
                "Days",
                "Value",
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

    private XYDataset createDataSet(Collection<SearchResult> results) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        // @TODO fill in the data here

        return dataset;
    }

    //    private XYDataset createDataset(String... names) {
//        int size = 50;
//        RandomNumber generator = new Normal(0.5, 10.0);
//        TimeSeriesCollection dataset = new TimeSeriesCollection();
//        int day = 01;
//        int month = 01;
//        int year = 2000;
//        for (String name : names) {
//            TimeSeries firefox = new TimeSeries(name);
//            Day current = new Day(day, month, year);
//            for (int i = 0; i < size; i++) {
//                firefox.add(current, generator.doubleValue());
//                current = (Day) current.next();
//            }
//            dataset.addSeries(firefox);
//        }
//
//        return dataset;
//    }
    @Override
    public void decorateAll(SearchResult searchResult) {
        // do nothing...
    }

    @Override
    public Image getIconImage() {
        return iconImage;
    }
}
