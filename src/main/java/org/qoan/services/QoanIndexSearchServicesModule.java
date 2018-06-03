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

package org.qoan.services;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.MapLoader;
import com.hazelcast.core.MapStoreFactory;
import org.qai.main.QaiConstants;
import org.qai.persistence.WikiArticle;
import org.qai.persistence.mapstores.WikiArticleMapStore;
import org.qai.services.SearchServiceInterface;
import org.qai.services.implementation.WikiSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class QoanIndexSearchServicesModule extends AbstractModule implements QaiConstants {

    private static Logger logger = LoggerFactory.getLogger("QoanModule");

    private String search_services = "search_services.";

    private String archives = "search_services.archives.";

    private String indices = " search_services.indices";

    private Map<String, SearchServiceInterface> providedSearchServices;

    private Properties properties;

    @Inject
    private Config config;

    public QoanIndexSearchServicesModule(Properties properties, Config config) {
        this.properties = properties;
        this.config = config;
        this.providedSearchServices = new ConcurrentHashMap<String, SearchServiceInterface>();
    }

    @Override
    protected void configure() {

        String contexts = properties.getProperty(search_services + "contexts");
        StringTokenizer tokenizer = new StringTokenizer(contexts, ",");
        while (tokenizer.hasMoreTokens()) {
            String context = tokenizer.nextToken();
            String archive = properties.getProperty(archives + context);
            String index = properties.getProperty(indices + context);
            SearchServiceInterface searchService = new WikiSearchService(WIKIPEDIA_EN, index, archive);
            provideServicesMap().put(context, searchService);
            createWikiMapStoreConfig(context, config);
        }
    }

    @Provides
    Map<String, SearchServiceInterface> provideServicesMap() {
        return providedSearchServices;
    }

    private void createWikiMapStoreConfig(String context, Config hazelcastConfig) {
        MapConfig mapConfig = hazelcastConfig.getMapConfig(context);
        mapConfig.getMapStoreConfig().setEnabled(true);
        MapStoreConfig mapStoreConfig = mapConfig.getMapStoreConfig();
        if (mapStoreConfig == null) {
            logger.info("mapStoreConfig is null... creating one for: " + context);
            mapStoreConfig = new MapStoreConfig();
        }

        String archive = properties.getProperty("search_services.archives." + context);
        WikiArticleMapStore wikiMapStore = new WikiArticleMapStore(archive);
        //startedMapstores.put(context, wikiMapStore);

        mapStoreConfig.setFactoryImplementation(new MapStoreFactory<String, WikiArticle>() {
            public MapLoader<String, WikiArticle> newMapStore(String mapName, Properties properties) {
                if (context.equals(mapName)) {
                    return wikiMapStore;
                } else {
                    return null;
                }
            }
        });
        logger.info("adding mapstore configuration for " + context);
        mapConfig.setMapStoreConfig(mapStoreConfig);
    }
}
