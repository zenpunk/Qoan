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
import com.google.inject.Singleton;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.qai.message.MessageQueue;
import org.qai.message.MessageQueueInterface;
import org.qai.security.ProcedureManagerInterface;
import org.qai.security.QaiSecurity;
import org.qai.security.QaiSecurityManager;
import org.qai.services.DistributedSearchServiceInterface;
import org.qai.services.ProcedureRunnerInterface;
import org.qai.services.UUIDServiceInterface;
import org.qai.services.implementation.DistributedSearchService;
import org.qai.services.implementation.ProcedureRunner;
import org.qai.services.implementation.UUIDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Created by rainbird on 11/19/15.
 */
public class QoanTestModule extends AbstractModule {

    private Logger logger = LoggerFactory.getLogger("QaiTestModule");

    private static String NODE_NAME = "QaiTestNode";

    private HazelcastInstance hazelcastInstance;

    private static String wikipediaDirectory = "/media/rainbird/GIMEL/wiki-archives/wikipedia_en.index";

    private static String wikipediaZipFileName = "/media/rainbird/GIMEL/wiki-archives/wikipedia_en.zip";

    private static String wiktionaryDirectory = "/media/rainbird/GIMEL/wiki-archives/wiktionary_en.index";

    private static String wiktionaryZipFileName = "/media/rainbird/GIMEL/wiki-archives/wiktionary_en.zip";

    private static String STOCK_QUOTES_DIRECTORY = "test/stockquotes/";

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<EntityManager>();

    @Override
    protected void configure() {

        // UUIDService
        bind(UUIDServiceInterface.class).to(UUIDService.class);

        // ProcedureSource
        //bind(ProcedureSourceInterface.class).to(ProcedureSourceService.class);

        // executorService
        bind(ProcedureRunnerInterface.class).to(ProcedureRunner.class);

        bind(ProcedureManagerInterface.class).to(QoanTestProcedureManager.class);

        // messageQueue
        bind(MessageQueueInterface.class).to(MessageQueue.class);

        bind(QaiSecurity.class).to(QaiSecurityManager.class);
    }

    @Provides
    Logger provideLogger() {
        return LoggerFactory.getLogger("Qoan");
    }

//    @Provides
//    @Named("USER")
//    public SearchServiceInterface provideUserDataService() {
//        SearchServiceInterface dataService = new ModelSearchService("./test/dummy.model.directory");
//        ((ModelSearchService) dataService).init();
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
    @Named("Users")
    DistributedSearchServiceInterface provideUsersSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("Users", getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("Wikipedia_en")
    DistributedSearchServiceInterface provideWikipediaSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("Wikipedia_en", getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("Wiktionary_en")
    DistributedSearchServiceInterface provideWiktionarySearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("Wiktionary_en", getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("WikiResources_en")
    DistributedSearchServiceInterface provideWikiResourcesSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("WikiResources_en", getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("StockEntities")
    DistributedSearchServiceInterface provideStockEntitiesSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("StockEntities", getHazelcastInstance());
        searchService.initialize();

        return searchService;
    }

    @Provides
    @Named("Procedures")
    DistributedSearchServiceInterface provideProceduresSearchServiceInterface() {
        DistributedSearchService searchService = new DistributedSearchService("Procedures", getHazelcastInstance());
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
