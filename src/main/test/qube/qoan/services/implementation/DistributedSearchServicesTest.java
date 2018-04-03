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
import qube.qai.services.DistributedSearchServiceInterface;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qai.user.User;
import qube.qoan.services.QoanInjectorService;
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
    private DistributedSearchServiceInterface userSearchService;

    @Inject
    @Named("Wikipedia_en")
    private DistributedSearchServiceInterface wikipediaSearchService;

    @Inject
    @Named("Wiktionary_en")
    private DistributedSearchServiceInterface wiktionarySearchService;

    @Inject
    @Named("WikiResources_en")
    private DistributedSearchServiceInterface wikiResourcesSearchService;

    @Inject
    @Named("Stock_Groups")
    private DistributedSearchServiceInterface stocksSearchService;

    @Inject
    @Named("Procedures")
    private DistributedSearchServiceInterface proceduresSearchService;

    @Inject
    @Named("MolecularResources")
    private DistributedSearchServiceInterface moleculeSearchService;

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        QoanInjectorService.getInstance().injectMembers(this);
    }

    public void testDistributedUserSearch() throws Exception {

        IMap<String, User> usersMap = hazelcastInstance.getMap(USERS);
        String userName = "Test_User";
        User dummyUser = new User(userName, "");
        //usersMap.put(dummyUser.getUuid(), dummyUser);

        // just assume there is something in there
        Collection<SearchResult> results = userSearchService.searchInputString("admin", USERS, 1);

        assertNotNull("there has to be some results", results);
        assertTrue("there has to be a user", !results.isEmpty());

    }

    public void testDistributedWikipediaSearch() throws Exception {

        String topicName = WIKIPEDIA;
        Collection<String> searchTopics = new ArrayList<>();
        //searchTopics.add("Mickey Mouse");
        searchTopics.add("mouse");

        checkSearchService(wikipediaSearchService, "titleString", searchTopics);
    }

    public void testDistributedWiktionarySearch() throws Exception {

        String topicName = WIKTIONARY;
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mouse");

        checkSearchService(wiktionarySearchService, "title", searchTopics);
    }

    public void testDistributedWikiResourcesSearch() throws Exception {

        String topicName = WIKIPEDIA_RESOURCES;
        Collection<String> searchTopics = new ArrayList<>();
        searchTopics.add("Mouse");

        checkSearchService(wikiResourcesSearchService, "title", searchTopics);
    }

    public void testDistributedStockGroupSearch() throws Exception {

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

    public void testDistributedProcedureSearch() throws Exception {

        Collection<SearchResult> results = proceduresSearchService.searchInputString("", "PdfResources", 50);
        assertNotNull("results may not be null", results);
        assertTrue("there have to be some results", !results.isEmpty());

    }

    public void testDistributedMolecularResSearch() throws Exception {

        Collection<SearchResult> results = moleculeSearchService.searchInputString("", "MolecularResources", 20);
        assertNotNull("results may not be null", results);
        assertTrue("there have to be some results", !results.isEmpty());
    }

    private void checkSearchService(SearchServiceInterface searchService, String fieldName, Collection<String> searchTopics) {

        for (String search : searchTopics) {
            Collection<SearchResult> results = searchService.searchInputString(search, fieldName, 100);
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



    private void log(String message) {
        System.out.println(message);
    }
}
