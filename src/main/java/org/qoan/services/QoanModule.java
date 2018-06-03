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
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.qai.main.QaiConstants;
import org.qai.persistence.*;
import org.qai.procedure.Procedure;
import org.qai.procedure.ProcedureLibrary;
import org.qai.procedure.ProcedureLibraryInterface;
import org.qai.security.ProcedureManager;
import org.qai.security.ProcedureManagerInterface;
import org.qai.security.QaiSecurity;
import org.qai.security.QaiSecurityManager;
import org.qai.services.DistributedSearchServiceInterface;
import org.qai.services.ProcedureRunnerInterface;
import org.qai.services.UUIDServiceInterface;
import org.qai.services.implementation.DistributedSearchService;
import org.qai.services.implementation.ProcedureRunner;
import org.qai.services.implementation.UUIDService;
import org.qoan.authentication.UserManagerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zenpunk on 11/2/15.
 */
public class QoanModule extends AbstractModule implements QaiConstants {

    private static Logger logger = LoggerFactory.getLogger("QoanModule");

    //public static final String CONFIG_FILE_NAME = "config_dev.properties";

    private boolean isServerQaiNode = true;

    // for the time being we leave it at that
    private static String QAI_NODE_NAME = "QaiNode";

    private Properties properties;

    private HazelcastInstance hazelcastInstance;

    private UserManagerInterface userManager;

    private DistributedSearchServiceInterface userSearchService;

    private DistributedSearchServiceInterface wikipediaSearchService;

    private DistributedSearchServiceInterface wiktionarySearchService;

    private DistributedSearchServiceInterface molecularResourcesService;

    private DistributedSearchServiceInterface pdfFileRsourcesService;

    private DistributedSearchServiceInterface wikiResourcesSearchService;

    private DistributedSearchServiceInterface stockEntitiesSearchService;

    private DistributedSearchServiceInterface stockGroupsSearchService;

    private DistributedSearchServiceInterface stockQuotesSearchService;

    private DistributedSearchServiceInterface proceduresSearchService;

    private Map<String, DistributedSearchServiceInterface> providedSearchServices;


    //@InjectConfig(value = "QAI_NODE_TO_CONNECT")

    //public String QAI_NODE_TO_CONNECT = "192.168.0.*:5701"; // localhost
    public String MONDAY_NODE = "192.168.0.199:5701"; // monday
    public String TUESDAY_NODE = "192.168.0.241:5701"; // tuesday
    public String WEDNESDAY_NODE = "192.168.0.164:5701"; // wednesday
    public String THURSDAY_NODE = "192.168.0.234:5701"; // thursday
    public String FRIDAY_NODE = "192.168.0.179:5701"; // friday
    public String STANN_NODE = "192.168.0.108:5701"; // stann
    //public String STANN_NODE = "127.0.0.1:5701";

    public String NODE_NAME = "NODE_NAME";

    private String GRID_NAME = "GRID_NAME";
    // private String GRID_NAME = "Qai-Nodes";

    private String GRID_PASSWORD = "GRID_PASSWORD";

    public QoanModule(Properties properties) {
        this.properties = properties;
        this.providedSearchServices = new ConcurrentHashMap<String, DistributedSearchServiceInterface>();

    }

    @Override
    protected void configure() {

        //install(ConfigurationModule.create());
        //requestInjection(this);

//        try {
//            Properties properties = new Properties();
//            properties.load(new FileReader(CONFIG_FILE_NAME));
//            Names.bindProperties(binder(), properties);
//        } catch (IOException e) {
//            logger.error("Error while loading configuration file: " + CONFIG_FILE_NAME, e);
//        }

        // Procedure execution-service
        bind(ProcedureRunnerInterface.class).to(ProcedureRunner.class);

        // QaiRealm
        bind(QaiSecurity.class).to(QaiSecurityManager.class);

        // Procedure Manager
        bind(ProcedureManagerInterface.class).to(ProcedureManager.class);

        bind(ProcedureLibraryInterface.class).to(ProcedureLibrary.class);

    }

    @Provides
    @Singleton
    Map<String, DistributedSearchServiceInterface> providedSearchServices() {
        return providedSearchServices;
    }

    @Provides
    @Singleton
    HazelcastInstance provideHazelcastInstance() {

        if (hazelcastInstance != null) {
            return hazelcastInstance;
        }

        ClientConfig config = new ClientConfig();
        config.setInstanceName(properties.getProperty(NODE_NAME));
        //config.getGroupConfig().setName(properties.getProperty(GRID_NAME));
        //config.getGroupConfig().setPassword(properties.getProperty(GRID_PASSWORD));
        config.getNetworkConfig().setSmartRouting(true);
        config.getNetworkConfig().setRedoOperation(true);
        // dev-config
        config.getNetworkConfig().addAddress(STANN_NODE);
        // deployment config
        //config.getNetworkConfig().addAddress(MONDAY_NODE, TUESDAY_NODE, WEDNESDAY_NODE, THURSDAY_NODE, FRIDAY_NODE);

        hazelcastInstance = HazelcastClient.newHazelcastClient(config);

        return hazelcastInstance;
    }

    @Provides
    Logger provideLogger() {
        return LoggerFactory.getLogger("Qoan");
    }

    @Provides
    @Named("ServicesMap")
    Map<String, DistributedSearchServiceInterface> provideServicesMap() {

        if (providedSearchServices == null || providedSearchServices.isEmpty()) {
            initKnownNamedServers();
        }

        return providedSearchServices;
    }

    @Provides
    @Singleton
    UUIDServiceInterface provideUUIDService() {
        return new UUIDService();
    }

    @Provides
    QaiDataProvider<Procedure> provideProcedureProvider() {
        QaiDataProvider<Procedure> provider = new MapDataProvider(hazelcastInstance, PROCEDURES);
        return provider;
    }

    @Provides
    QaiDataProvider<StockGroup> provideStockGroupProvider() {
        QaiDataProvider<StockGroup> provider = new MapDataProvider(hazelcastInstance, STOCK_GROUPS);
        return provider;
    }

    @Provides
    QaiDataProvider<StockEntity> provideStockEntityProvider() {
        QaiDataProvider<StockEntity> provider = new MapDataProvider(hazelcastInstance, STOCK_ENTITIES);
        return provider;
    }

    @Provides
    QaiDataProvider<StockQuote> provideStockQuoteProvider() {
        QaiDataProvider<StockQuote> provider = new MapDataProvider(hazelcastInstance, STOCK_QUOTES);
        return provider;
    }

    @Provides
    QaiDataProvider<WikiArticle> provideWikiArticleData() {
        QaiDataProvider<WikiArticle> provider = new MapDataProvider(hazelcastInstance, WIKIPEDIA_EN);
        return provider;
    }

    @Provides
    @Named("WikiResources_en")
    QaiDataProvider<ResourceData> provideWikiResourceProvider() {
        QaiDataProvider<ResourceData> provider = new MapDataProvider(hazelcastInstance, WIKIPEDIA_RESOURCES);
        return provider;
    }

    @Provides
    @Named("PdfFileResources")
    QaiDataProvider<ResourceData> providePdfResourceProvider() {
        QaiDataProvider<ResourceData> provider = new MapDataProvider(hazelcastInstance, PDF_FILE_RESOURCES);
        return provider;
    }

    @Provides
    @Named("Users")
    @Singleton
    DistributedSearchServiceInterface provideUserSearchService() {

        userSearchService = new DistributedSearchService(USERS, hazelcastInstance);
        userSearchService.initialize();

        return userSearchService;
    }

    @Provides
    @Named("Wikipedia_en")
    @Singleton
    public DistributedSearchServiceInterface provideWikipediaSearchService() {

        wikipediaSearchService = new DistributedSearchService(WIKIPEDIA_EN, hazelcastInstance);
        wikipediaSearchService.initialize();

        return wikipediaSearchService;
    }

    @Provides
    @Named("Wiktionary_en")
    @Singleton
    public DistributedSearchServiceInterface provideWiktionarySearchService() {

        wiktionarySearchService = new DistributedSearchService(WIKTIONARY_EN, hazelcastInstance);
        wiktionarySearchService.initialize();

        return wiktionarySearchService;
    }

    @Provides
    @Named("Stock_Groups")
    @Singleton
    DistributedSearchServiceInterface provideStockGroupssSearchService() {

        stockGroupsSearchService = new DistributedSearchService(STOCK_GROUPS, hazelcastInstance);
        stockGroupsSearchService.initialize();

        return stockGroupsSearchService;
    }

    @Provides
    @Named("Stock_Quotes")
    @Singleton
    DistributedSearchServiceInterface provideStockQuotessSearchService() {

        stockQuotesSearchService = new DistributedSearchService(STOCK_QUOTES, hazelcastInstance);
        stockQuotesSearchService.initialize();

        return stockQuotesSearchService;
    }

    @Provides
    @Named("Stock_Entities")
    @Singleton
    DistributedSearchServiceInterface provideStockEntitiesSearchService() {

        stockEntitiesSearchService = new DistributedSearchService(STOCK_ENTITIES, hazelcastInstance);
        stockEntitiesSearchService.initialize();

        return stockEntitiesSearchService;
    }

    @Provides
    @Named("Procedures")
    @Singleton
    DistributedSearchServiceInterface provideProceduresSearchService() {

        proceduresSearchService = new DistributedSearchService(PROCEDURES, hazelcastInstance);

        proceduresSearchService.initialize();

        return proceduresSearchService;
    }

    @Provides
    @Named("WikiResources_en")
    @Singleton
    DistributedSearchServiceInterface provideWikiResourcesSearchService() {

        wikiResourcesSearchService = new DistributedSearchService(WIKIPEDIA_RESOURCES, hazelcastInstance);
        wikiResourcesSearchService.initialize();

        return wikiResourcesSearchService;
    }

    @Provides
    @Named("MolecularResources")
    @Singleton
    DistributedSearchServiceInterface provideMolecularSearchService() {

        molecularResourcesService = new DistributedSearchService(MOLECULAR_RESOURCES, hazelcastInstance);
        molecularResourcesService.initialize();

        return molecularResourcesService;
    }

    @Provides
    @Named("PdfFileResources")
    @Singleton
    DistributedSearchServiceInterface providePdfFileSearchService() {

        pdfFileRsourcesService = new DistributedSearchService(PDF_FILE_RESOURCES, hazelcastInstance);
        pdfFileRsourcesService.initialize();

        return pdfFileRsourcesService;
    }

    public DistributedSearchServiceInterface getNamedService(String name) {

        if (providedSearchServices == null || providedSearchServices.isEmpty()) {
            initKnownNamedServers();
        }

        return providedSearchServices.get(name);
    }

    /**
     * when a new service is created, use this to be able to use it
     *
     * @param name
     * @param service
     */
    public void addNamedSearchService(String name, DistributedSearchService service) {

        if (providedSearchServices == null || providedSearchServices.isEmpty()) {
            initKnownNamedServers();
        }

        providedSearchServices.put(name, service);
    }

    public Set<String> getSearchServiceNames() {

        initKnownNamedServers();

        return providedSearchServices.keySet();
    }

    private void initKnownNamedServers() {

        if (wikipediaSearchService == null) {
            wikipediaSearchService = provideWikipediaSearchService();
            logger.info("Started service: " + WIKIPEDIA_EN);
            providedSearchServices.put(WIKIPEDIA_EN, wikipediaSearchService);
        } else if (!providedSearchServices.containsKey(WIKIPEDIA_EN)) {
            providedSearchServices.put(WIKIPEDIA_EN, wikipediaSearchService);
        }

        if (wiktionarySearchService == null) {
            wiktionarySearchService = provideWiktionarySearchService();
            logger.info("Started service: " + WIKTIONARY_EN);
            providedSearchServices.put(WIKTIONARY_EN, wiktionarySearchService);
        } else if (!providedSearchServices.containsKey(WIKTIONARY_EN)) {
            providedSearchServices.put(WIKTIONARY_EN, wiktionarySearchService);
        }

        if (wikiResourcesSearchService == null) {
            wikiResourcesSearchService = provideWikiResourcesSearchService();
            logger.info("Started service: " + WIKIPEDIA_RESOURCES);
            providedSearchServices.put(WIKIPEDIA_RESOURCES, wikiResourcesSearchService);
        } else if (!providedSearchServices.containsKey(WIKIPEDIA_RESOURCES)) {
            providedSearchServices.put(WIKIPEDIA_RESOURCES, wikiResourcesSearchService);
        }

        if (stockEntitiesSearchService == null) {
            stockEntitiesSearchService = provideStockEntitiesSearchService();
            logger.info("Started service: " + STOCK_ENTITIES);
            providedSearchServices.put(STOCK_ENTITIES, stockEntitiesSearchService);
        } else if (!providedSearchServices.containsKey(STOCK_ENTITIES)) {
            providedSearchServices.put(STOCK_ENTITIES, stockEntitiesSearchService);
        }

        if (stockGroupsSearchService == null) {
            stockGroupsSearchService = provideStockGroupssSearchService();
            logger.info("Started service: " + STOCK_GROUPS);
            providedSearchServices.put(STOCK_GROUPS, stockGroupsSearchService);
        } else if (!providedSearchServices.containsKey(STOCK_GROUPS)) {
            providedSearchServices.put(STOCK_GROUPS, stockGroupsSearchService);
        }

        if (proceduresSearchService == null) {
            proceduresSearchService = provideProceduresSearchService();
            logger.info("Started service: " + PROCEDURES);
            providedSearchServices.put(PROCEDURES, proceduresSearchService);
        } else if (!providedSearchServices.containsKey(PROCEDURES)) {
            providedSearchServices.put(PROCEDURES, proceduresSearchService);
        }

        if (molecularResourcesService == null) {
            molecularResourcesService = provideMolecularSearchService();
            logger.info("Started service: " + MOLECULAR_RESOURCES);
            providedSearchServices.put(MOLECULAR_RESOURCES, molecularResourcesService);
        } else if (!providedSearchServices.containsKey(MOLECULAR_RESOURCES)) {
            providedSearchServices.put(MOLECULAR_RESOURCES, molecularResourcesService);
        }

        if (pdfFileRsourcesService == null) {
            pdfFileRsourcesService = providePdfFileSearchService();
            logger.info("Started service: " + PDF_FILE_RESOURCES);
            providedSearchServices.put(PDF_FILE_RESOURCES, pdfFileRsourcesService);
        } else if (!providedSearchServices.containsKey(PDF_FILE_RESOURCES)) {
            providedSearchServices.put(PDF_FILE_RESOURCES, pdfFileRsourcesService);
        }

    }

    private HazelcastInstance getHazelcastInstance() {

        if (hazelcastInstance == null) {
            hazelcastInstance = provideHazelcastInstance();
        }

        return hazelcastInstance;
    }

}
