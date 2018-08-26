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
import org.qai.main.QaiConstants;
import org.qai.services.DistributedSearchServiceInterface;
import org.qai.services.implementation.DistributedNetworkBuilder;
import org.qai.services.implementation.DistributedSearchService;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class QoanServicesModule extends AbstractModule implements QaiConstants {

    private static String PROP_NETWORK_BUILDERS = "network_builders.contexts";

    private static String PROP_SEARCH_SERVICES = "search_services.contexts";

    private String[] propertyNames = {"search_services_archives.wikipedia_en",
            "search_services_indices.wikipedia_en",
            "search_services_archives.wiktionary_en"};


    private Map<String, DistributedSearchServiceInterface> providedSearchServices;

    private Map<String, DistributedNetworkBuilder> providedNetworkBuilders;

    private Properties properties;

    public QoanServicesModule(Properties properties) {
        this.properties = properties;
        this.providedSearchServices = new ConcurrentHashMap<>();
        this.providedNetworkBuilders = new ConcurrentHashMap<>();
    }

    @Override
    protected void configure() {

        String allSearchServices = properties.getProperty(PROP_SEARCH_SERVICES);
        String[] searchServices = allSearchServices.split(",", 100);
        for (int i = 0; i < searchServices.length; i++) {

            DistributedSearchServiceInterface searchService = new DistributedSearchService(searchServices[i]);
            providedSearchServices.put(searchServices[i], searchService);
        }

        String allNetworkBuilders = properties.getProperty(PROP_NETWORK_BUILDERS);
        String[] networkBuilders = allNetworkBuilders.split(",", 100);
        for (int i = 0; i < networkBuilders.length; i++) {

            DistributedNetworkBuilder networkBuilder = new DistributedNetworkBuilder(networkBuilders[i]);
            providedNetworkBuilders.put(networkBuilders[i], networkBuilder);
        }

    }

    @Provides
    public Map<String, DistributedSearchServiceInterface> provideServicesMap() {
        return providedSearchServices;
    }

    @Provides
    public Map<String, DistributedNetworkBuilder> provideNetworkBuildersMap() {
        return providedNetworkBuilders;
    }
}
