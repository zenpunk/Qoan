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
import org.qai.services.implementation.DistributedSearchService;

import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class QoanSearchServicesModule extends AbstractModule implements QaiConstants {

    private String search_services = "search_services.";

    private String indices = " search_services.indices";

    private Map<String, DistributedSearchServiceInterface> providedSearchServices;

    private Properties properties;

    public QoanSearchServicesModule(Properties properties) {
        this.properties = properties;
        this.providedSearchServices = new ConcurrentHashMap<String, DistributedSearchServiceInterface>();
    }

    @Override
    protected void configure() {

        String contexts = properties.getProperty(search_services + "contexts");
        StringTokenizer tokenizer = new StringTokenizer(contexts, ",");
        while (tokenizer.hasMoreTokens()) {
            String context = tokenizer.nextToken();
            DistributedSearchServiceInterface searchService = new DistributedSearchService(context);
            provideServicesMap().put(context, searchService);
        }
    }

    @Provides
    Map<String, DistributedSearchServiceInterface> provideServicesMap() {
        return providedSearchServices;
    }
}
