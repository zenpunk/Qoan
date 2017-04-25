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

package qube.qoan.services.implementation;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rainbird on 1/6/16.
 */
public class TestDistributedSearchServices extends QoanTestBase {

    private Logger logger = LoggerFactory.getLogger("QaiTestModule");

    @Inject
    @Named("Users")
    private SearchServiceInterface userSearchService;

    @Inject
    @Named("Wikipedia_en")
    private SearchServiceInterface wikipediaSearchService;

    @Inject
    @Named("Wiktionary_en")
    private SearchServiceInterface wiktionarySearchService;

    @Inject
    @Named("WikiResources_en")
    private SearchServiceInterface wikiResourcesSearchService;

    @Inject
    @Named("StockEntities")
    private SearchServiceInterface stocksSearchService;

    @Inject
    @Named("Procedures")
    private SearchServiceInterface proceduresSearchService;

    @Inject
    private HazelcastInstance hazelcastInstance;

    public void testDistributedUserSearch() throws Exception {

        String topicName = "Users";
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Test_User");

        checkSearchService(topicName, searchTopics);
    }

    public void testDistributedWikipediaSearch() throws Exception {

        String topicName = "Wikipedia_en";
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mickey Mouse");

        checkSearchService(topicName, searchTopics);
    }

    public void testDistributedWiktionarySearch() throws Exception {

        String topicName = "Wiktionary_en";
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mouse");

        checkSearchService(topicName, searchTopics);
    }

    public void testDistributedWikiResourcesSearch() throws Exception {

        String topicName = "WikiResources_en";
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mouse");

        checkSearchService(topicName, searchTopics);
    }

    public void testDistributedStockEntitySearch() throws Exception {

        String topicName = "Stock_Entities";
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("GOOG");

        checkSearchService(topicName, searchTopics);
    }

    public void testDistributedProcedureSearch() throws Exception {

        String topicName = "Procedures";
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("*");

        checkSearchService(topicName, searchTopics);
    }

    private void checkSearchService(String topicName, Collection<String> searchTopics) {

        SearchServiceInterface distributedSearch = getSearchListener(topicName);
        IMap<String, Object> topicMap = hazelcastInstance.getMap(topicName);

        for (String search : searchTopics) {
            Collection<SearchResult> results = distributedSearch.searchInputString(search, topicName, 100);
            assertNotNull("have to return something", results);
            assertTrue("has to be something in there as well", !results.isEmpty());
            for (SearchResult result : results) {
                logger.info("found result: " + result.getContext());
                Object resultObject = topicMap.get(result.getUuid());
                assertNotNull("there has to be a corresponding object", resultObject);
            }
        }
    }

    private SearchServiceInterface getSearchListener(String topicName) {

        if ("Users".equals(topicName)) {
            return userSearchService;
        } else if ("Wikipedia_en".equals(topicName)) {
            return wikipediaSearchService;
        } else if ("Wiktionary_en".equals(topicName)) {
            return wiktionarySearchService;
        } else if ("WikiResources_en".equals(topicName)) {
            return wikiResourcesSearchService;
        } else if ("Stock_Entities".equals(topicName)) {
            return stocksSearchService;
        } else if ("Procedures".equals(topicName)) {
            return proceduresSearchService;
        }

        return null;
    }

}
