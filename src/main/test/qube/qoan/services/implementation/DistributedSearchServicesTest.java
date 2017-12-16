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

package qube.qoan.services.implementation;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.persistence.StockEntity;
import qube.qai.persistence.StockGroup;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qai.user.User;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by rainbird on 1/6/16.
 */
public class DistributedSearchServicesTest extends QoanTestBase {

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

        IMap<String, User> usersMap = hazelcastInstance.getMap(USERS);
        String userName = "Test_User";
        User dummyUser = new User(userName, "");
        //usersMap.put(dummyUser.getUuid(), dummyUser);

        // just assume there is something in there
        Collection<SearchResult> results = userSearchService.searchInputString(USERS, "*", 1);

        assertNotNull("there has to be some results", results);
        assertTrue("there has to be a user", !results.isEmpty());

    }

    public void testDistributedProcedureSearch() throws Exception {

        String topicName = PROCEDURES;
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("*");

        checkSearchService(topicName, "name", searchTopics);
    }

    public void testDistributedWikipediaSearch() throws Exception {

        String topicName = WIKIPEDIA;
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mickey Mouse");

        checkSearchService(topicName, "titleString", searchTopics);
    }

    public void testDistributedWiktionarySearch() throws Exception {

        String topicName = WIKTIONARY;
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mouse");

        checkSearchService(topicName, "titleString", searchTopics);
    }

    public void testDistributedWikiResourcesSearch() throws Exception {

        String topicName = WIKIPEDIA_RESOURCES;
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mouse");

        checkSearchService(topicName, "titleString", searchTopics);
    }

    public void testDistributedGroupSearch() throws Exception {

        Collection<SearchResult> results = stocksSearchService.searchInputString("*", STOCK_GROUPS, 0);

        assertNotNull("there has to be a result", results);
        assertTrue("the list must be populated", results.size() > 0);

        IMap<String, StockGroup> groupMap = hazelcastInstance.getMap(STOCK_GROUPS);
        for (SearchResult result : results) {
            StockGroup group = groupMap.get(result.getUuid());
            assertNotNull("found group must exist", group);
            // obviously this doesn't work
            Set<StockEntity> entities = group.getEntities();
            assertNotNull("there has to be some entities", entities);
            assertTrue("the set may not be empty", !entities.isEmpty());
            for (StockEntity entity : entities) {
                log("StockEntity: " + entity.getName());
            }
        }
    }

    public void estDistributedProcedureSearch() throws Exception {

        String topicName = PROCEDURES;
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("*");

        checkSearchService(topicName, "", searchTopics);
    }

    private void checkSearchService(String topicName, String fieldName, Collection<String> searchTopics) {

        SearchServiceInterface distributedSearch = getSearchListener(topicName);

        for (String search : searchTopics) {
            Collection<SearchResult> results = distributedSearch.searchInputString(search, fieldName, 100);
            assertNotNull("have to return something", results);
            assertTrue("has to be something in there as well", !results.isEmpty());
            for (SearchResult result : results) {
                logger.info("found result: " + result.getContext());
                IMap<String, Object> topicMap = hazelcastInstance.getMap(result.getContext());
                Object resultObject = topicMap.get(result.getUuid());
                assertNotNull("there has to be a corresponding object", resultObject);
            }
        }
    }

    private SearchServiceInterface getSearchListener(String topicName) {

        if (USERS.equals(topicName)) {
            return userSearchService;
        } else if (WIKIPEDIA.equals(topicName)) {
            return wikipediaSearchService;
        } else if (WIKTIONARY.equals(topicName)) {
            return wiktionarySearchService;
        } else if (WIKIPEDIA_RESOURCES.equals(topicName)) {
            return wikiResourcesSearchService;
        } else if (STOCK_GROUPS.equals(topicName)) {
            return stocksSearchService;
        } else if (PROCEDURES.equals(topicName)) {
            return proceduresSearchService;
        }

        return null;
    }

    private void log(String message) {
        System.out.println(message);
    }
}
