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

package qube.qoan.services;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.message.MessageQueue;
import qube.qai.message.MessageQueueInterface;
import qube.qai.services.*;
import qube.qai.services.implementation.*;

import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Created by rainbird on 11/19/15.
 */
public class QoanTestModule extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger("QaiTestModule");

    private static String NODE_NAME = "QaiTestNode";

    private HazelcastInstance hazelcastInstance;

    private static String wikipediaDirectory = "/media/rainbird/ALEPH/wiki-archives/wikipedia_en.index";

    private static String wikipediaZipFileName = "/media/rainbird/ALEPH/wiki-archives/wikipedia_en.zip";

    private static String wiktionaryDirectory = "/media/rainbird/ALEPH/wiki-archives/wiktionary_en.index";

    private static String wiktionaryZipFileName = "/media/rainbird/ALEPH/wiki-archives/wiktionary_en.zip";

    private static String STOCK_QUOTES_DIRECTORY = "test/stockquotes/";

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<EntityManager>();

    @Override
    protected void configure() {

        // UUIDService
        bind(UUIDServiceInterface.class).to(UUIDService.class);

        // ProcedureSource
        bind(ProcedureSourceInterface.class).to(ProcedureSourceService.class);

        // executorService
        bind(ProcedureRunnerInterface.class).to(ProcedureRunner.class);

        // messageQueue
        bind(MessageQueueInterface.class).to(MessageQueue.class);
    }

//    @Provides
//    @Named("USER")
//    public SearchServiceInterface provideUserDataService() {
//        SearchServiceInterface dataService = new ModelStore("./test/dummy.model.directory");
//        ((ModelStore) dataService).init();
//        return dataService;
//    }

//    @Provides
//    @Singleton
//    public EntityManagerFactory provideEntityManagerFactory() {
//        Map<String, String> properties = new HashMap<String, String>();
//        properties.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
//        properties.put("hibernate.connection.url", "jdbc:hsqldb:" + STOCK_QUOTES_DIRECTORY);
//        properties.put("hibernate.connection.username", "sa");
//        properties.put("hibernate.connection.password", "");
//        properties.put("hibernate.connection.pool_size", "1");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        properties.put("hibernate.hbm2ddl.auto", "create");
//
//        properties.put("current_session_context_class", "org.hibernate.context.ManagedSessionContext");
//        properties.put("hibernate.cache.use_second_level_cache", "false");
//        properties.put("hibernate.cache.use_query_cache", "false");
//        properties.put("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
//        properties.put("show_sql", "true");
//
//        return Persistence.createEntityManagerFactory("db-manager", properties);
//    }
//
//    @Provides
//    public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
//        EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
//        if (entityManager == null) {
//            ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory.createEntityManager());
//        }
//        return entityManager;
//    }

    @Provides
    @Singleton
    public HazelcastInstance provideHazelcastInstance() {
        if (hazelcastInstance != null) {
            return hazelcastInstance;
        }

        ClientConfig clientConfig = new ClientConfig();
        //clientConfig.setInstanceName(NODE_NAME);
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");
        hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

        return hazelcastInstance;
    }

    @Provides
    SelectorFactoryInterface provideSelectorFactoryInterface() {
        SelectorFactoryInterface selectorfactory = new DataSelectorFactory();

        return selectorfactory;
    }

    @Provides
    @Named("Users")
    SearchServiceInterface provideUsersSearchServiceInterface() {
        SearchServiceInterface searchService = new DistributedSearchService("Users");

        return searchService;
    }

    @Provides
    @Named("Wikipedia_en")
    SearchServiceInterface provideWikipediaSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("Wikipedia_en");

        searchService.setHazelcastInstance(getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("Wiktionary_en")
    SearchServiceInterface provideWiktionarySearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("Wiktionary_en");

        searchService.setHazelcastInstance(getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("WikiResources_en")
    SearchServiceInterface provideWikiResourcesSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("WikiResources_en");

        searchService.setHazelcastInstance(getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("StockEntities")
    SearchServiceInterface provideStockEntitiesSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("StockEntities");

        searchService.setHazelcastInstance(getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("Procedures")
    SearchServiceInterface provideProceduresSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("Procedures");

        searchService.setHazelcastInstance(getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    private HazelcastInstance getHazelcastInstance() {

        if (hazelcastInstance == null) {
            hazelcastInstance = provideHazelcastInstance();
        }

        return hazelcastInstance;
    }
}
