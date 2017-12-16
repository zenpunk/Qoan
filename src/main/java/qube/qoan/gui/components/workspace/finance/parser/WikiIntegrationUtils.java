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

package qube.qoan.gui.components.workspace.finance.parser;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.vaadin.ui.Layout;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import qube.qai.parsers.WikiIntegration;
import qube.qai.persistence.StockEntity;
import qube.qai.persistence.WikiArticle;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/21/15.
 */
public class WikiIntegrationUtils {

    @Inject
    private Logger logger;

    @Inject
    private HazelcastInstance hazelcastInstance;

    private String STOCK_ENTITIES = "STOCK_ENTITIES";

    /**
     * this at least at this point, a class to integrate
     * text from wiki-articles in vaadin surrounding.
     * for starters, we will be converting wiki-tables
     * to vaadin tables, so that they can be used in
     * the gui and allow better user-interaction
     */
    public WikiIntegrationUtils() {
    }

    public WikiIntegrationUtils(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }
//
//    public Table convertHtmlTable(WikiArticle wikiArticle) {
//        String html = WikiIntegration.wikiToHtml(wikiArticle);
//        return convertHtmlTable(wikiArticle.getTitle(), html);
//    }
//
//    public Table convertHtmlTable(String title, String html) {
//
//        String[] header = WikiIntegration.stripHeader(html);
//        String[][] data = WikiIntegration.stripTableData(html);
//
//        Table table = new Table();
//        table.setVisible(true);
//        table.setSelectable(true);
//        table.setImmediate(true);
//        table.setDragMode(Table.TableDragMode.ROW);
//        table.setColumnReorderingAllowed(true);
//        table.setColumnCollapsingAllowed(true);
//        table.setFooterVisible(true);
//        table.setSortAscending(true);
//        table.setPageLength(9);
//        //table.setSizeUndefined();
//        // begin with adding the headers
//        for (int i = 0; i < header.length; i++) {
//            table.addContainerProperty(header[i], String.class, null);
//        }
//
//        // now add the data
//        for (int i = 0; i < data.length; i++) {
//            Object itemId = table.addItem();
//            Item row = table.getItem(itemId);
//            for (int j = 0; j < data[i].length; j++) {
//                row.getItemProperty(header[j]).setValue(data[i][j]);
//            }
//        }
//
//        if (hazelcastInstance != null) {
//            insertEntitiesToMap(header, data);
//        }
//
//        return table;
//    }

    /**
     * @param header
     * @param data
     * @TODO make a test for this method
     */
    private void insertEntitiesToMap(String[] header, String[][] data) {

        IMap<String, StockEntity> entityMap = hazelcastInstance.getMap(STOCK_ENTITIES);

        for (int i = 0; i < data.length; i++) {

            StockEntity entity = new StockEntity();

            try {
                for (int j = 0; j < data[i].length; j++) {

                    if ((StringUtils.startsWith(header[j], "Ticker symbol"))) {
                        String tradedIn = StringUtils.substringBetween(data[i][j], "{{", "|");
                        String ticker = StringUtils.substringBetween(data[i][j], "|", "}}");
                        entity.setTradedIn(tradedIn);
                        entity.setTickerSymbol(ticker);
                    } else if (StringUtils.startsWith(header[j], "Security")) {
                        entity.setName(data[i][j]);
                        entity.setSecurity(data[i][j]);
                    } else if (StringUtils.startsWith(header[j], "SEC filings")) {
                        entity.setSecFilings(data[i][j]);
                    } else if (StringUtils.startsWith(header[j], "GICS") && !StringUtils.startsWith(header[j], "GICS Sub Industry")) {
                        entity.setGicsSector(data[i][j]);
                    } else if (StringUtils.startsWith(header[j], "GICS Sub Industry")) {
                        entity.setGicsSubIndustry(data[i][j]);
                    } else if (StringUtils.startsWith(header[j], "Address")) {
                        entity.setAddress(data[i][j]);
                    } else if (StringUtils.startsWith(header[j], "Date") && StringUtils.isNotBlank(data[i][j])) {
                        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                        DateTime dateTime = formatter.parseDateTime(data[i][j]);
                        entity.setDateFirstAdded(dateTime.toDate());
                    } else if (StringUtils.startsWith(header[j], "CIK")) {
                        entity.setCIK(data[i][j]);
                    }
                }
            } catch (Exception e) {
                logger.info("Skipping entity due to exception: " + e.getMessage());
                break;
            }
            // and now add the create entity to the map if it is not already there
            if (!entityMap.containsKey(entity.getUuid())) {
                entityMap.put(entity.getUuid(), entity);
            }
        }
    }

    /**
     * method adds contents of the wiki-article to the layout
     * images and tables will be replaced by vaadin images and tables
     *
     * @param article
     * @param layout
     */
    public void addWikiContentsToLayout(WikiArticle article, Layout layout) {

        String html = WikiIntegration.wikiToHtml(article);

        logger.info(html);
        // @TODO
    }
}
