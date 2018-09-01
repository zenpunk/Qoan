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

package org.qoan.services.implementation;

import com.hazelcast.core.HazelcastInstance;
import junit.framework.TestCase;
import org.qai.main.QaiServerModule;
import org.qai.services.DistributedSearchServiceInterface;
import org.qai.services.SearchResultSink;
import org.qai.services.implementation.SearchResult;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Future;

public class QaiIntegrationNetworkBuildersTest extends TestCase {

    private String propertiesFileNmae = "org/qoan/services/config_dev.properties";

    private String prop_contexts = "search_services.contexts";

    private Properties properties;

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Inject
    private Map<String, DistributedSearchServiceInterface> searchServices;

    private Map<String, String> searchTerms = new HashMap<>();

    private SearchResultSink dummySink = new SearchResultSink() {
        @Override
        public String getContext() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void delayedResults(Collection<SearchResult> results) {

        }

        @Override
        public void doSearch(String searchString) {

        }

        @Override
        public Collection<SearchResult> getCurrentResult() {
            return null;
        }
    };

    @Override
    protected void setUp() throws Exception {

        properties = new Properties();

        try {
            ClassLoader loader = QaiServerModule.class.getClassLoader();
            URL url = loader.getResource(propertiesFileNmae);
            properties.load(url.openStream());

        } catch (IOException e) {
            log("Error while loading configuration file: " + propertiesFileNmae + " with exception: " + e);
            throw new RuntimeException("Configuration file: '" + propertiesFileNmae + "' could not be found- have to exit!");
        }

        searchTerms.put("wikipedia_ar", "فيينا");
        searchTerms.put("wikipedia_de", "Wien");
        searchTerms.put("wikipedia_en", "Vienna");
        searchTerms.put("wikipedia_es", "Viena");
        searchTerms.put("wikipedia_fr", "Vienne");
        searchTerms.put("wikipedia_it", "Vienna");
        searchTerms.put("wikipedia_pt", "Viena");
        searchTerms.put("wikipedia_ru", "Вена");
        searchTerms.put("wikipedia_se", "Wien");
        searchTerms.put("wikipedia_zh", "維也納");
    }

    public void testDistributedUserSearch() throws Exception {

        assertNotNull("configuration error, there is not searchServices map", searchServices);
        assertNotNull("configuration error, there is no hazelcastInstance", hazelcastInstance);
        assertNotNull("configuration error, there is no properties", properties);

        String allContexts = properties.getProperty(prop_contexts);
        String[] contexts = allContexts.split(",", 100);
        log("found contexts:");
        log(Arrays.toString(contexts));


        for (int i = 0; i < contexts.length; i++) {
            DistributedSearchServiceInterface searchService = searchServices.get(contexts[i]);
            assertNotNull("there has to be a search service corresponding to: " + contexts[i], searchService);
            Future<Collection<SearchResult>> resultsFuture = searchService.searchInputString(dummySink, searchTerms.get(contexts[i]), "title", 100);
            assertNotNull("there has to be a future handle", resultsFuture);
            assertTrue("results should be coming eventually", resultsFuture.isDone());
            assertNotNull("there has to be actual results", resultsFuture.get());
        }

    }


    private void log(String message) {
        System.out.println(message);
    }
}
