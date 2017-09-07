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
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.main.QaiConstants;
import qube.qai.persistence.*;
import qube.qai.procedure.Procedure;
import qube.qai.security.QaiRealm;
import qube.qai.security.QaiSecurity;
import qube.qai.services.*;
import qube.qai.services.implementation.CachedProcedureSourceService;
import qube.qai.services.implementation.DistributedSearchService;
import qube.qai.services.implementation.ProcedureRunner;
import qube.qai.services.implementation.UUIDService;
import qube.qoan.authentication.UserManager;
import qube.qoan.authentication.UserManagerInterface;
import qube.qoan.gui.components.common.search.DocumentSearchSink;
import qube.qoan.gui.components.common.search.FinanceSearchSink;
import qube.qoan.gui.components.common.search.ProcedureSearchSink;
import qube.qoan.gui.components.common.search.WikiSearchSink;
import qube.qoan.gui.components.workspace.resource.ResourceSearchSink;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by rainbird on 11/2/15.
 */
//@BindConfig(value = "qube/qoan/services/config_dev", syntax = Syntax.PROPERTIES)
//@BindConfig(value = "qube/qoan/services/config_deploy", syntax = Syntax.PROPERTIES)
public class QoanModule extends AbstractModule implements QaiConstants {

    private static Logger logger = LoggerFactory.getLogger("QoanModule");

    public static final String CONFIG_FILE_NAME = "config_dev.properties";

    private boolean isServerQaiNode = true;

    // for the time being we leave it at that
    private static String QAI_NODE_NAME = "QaiNode";

    private HazelcastInstance hazelcastInstance;

    private UserManagerInterface userManager;

    private WikiSearchSink wikiSearchSink;

    private FinanceSearchSink financeResultSink;

    private ProcedureSearchSink procedureResultSink;

    private ResourceSearchSink resourceSearchSink;

    private DocumentSearchSink documentSearchSink;

    private SearchServiceInterface userSearchService;

    private SearchServiceInterface wikipediaSearchService;

    private SearchServiceInterface wiktionarySearchService;

    private SearchServiceInterface molecularResourcesService;

    private SearchServiceInterface pdfFileRsourcesService;

    private SearchServiceInterface wikiResourcesSearchService;

    private SearchServiceInterface stockEntitiesSearchService;

    private SearchServiceInterface stockGroupsSearchService;

    private SearchServiceInterface stockQuotesSearchService;

    private SearchServiceInterface proceduresSearchService;

    private Map<String, SearchServiceInterface> namedSearchServices;


    //@InjectConfig(value = "QAI_NODE_TO_CONNECT")
    public String QAI_NODE_TO_CONNECT = "127.0.0.1:5701";



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

        // executorService
        bind(ProcedureRunnerInterface.class).to(ProcedureRunner.class);

        // QaiRealm
        bind(QaiSecurity.class).to(QaiRealm.class);
    }

    @Provides
    @Singleton
    UUIDServiceInterface provideUUIDService() {
        return new UUIDService();
    }

    @Provides
    ProcedureSourceInterface provideProcedureSourceInterface() {
        return CachedProcedureSourceService.getInstance();
    }

    @Provides
    @Singleton
    ProcedureCache provideProcedureSource() {
        return new ProcedureCache();
    }

    @Provides
    @Singleton
    @Named("WikiResults")
    SearchResultSink provideSearchResultSink() {
        wikiSearchSink = new WikiSearchSink();
        return wikiSearchSink;
    }

    @Provides
    @Singleton
    @Named("ProcedureResults")
    SearchResultSink provideProcedureResultSink() {
        procedureResultSink = new ProcedureSearchSink();
        return procedureResultSink;
    }

    @Provides
    @Singleton
    @Named("FinanceResults")
    SearchResultSink provideFinanceResultSink() {
        financeResultSink = new FinanceSearchSink();
        return financeResultSink;
    }

    @Provides
    @Singleton
    @Named("ResourceResults")
    SearchResultSink provideDocumentSearchSink() {
        documentSearchSink = new DocumentSearchSink();
        return documentSearchSink;
    }

    @Provides
    @Singleton
    UserManagerInterface provideUserManager() {

        if (userManager == null) {
            userManager = new UserManager();
        }

        return userManager;
    }

    @Provides
    QaiDataProvider<Procedure> provideProcedureProvier() {
        QaiDataProvider<Procedure> provider = new MapDataProvider(PROCEDURES, hazelcastInstance);
        return provider;
    }

    @Provides
    QaiDataProvider<StockGroup> provideStockGroupProvider() {
        QaiDataProvider<StockGroup> provider = new MapDataProvider(STOCK_GROUPS, hazelcastInstance);
        return provider;
    }

    @Provides
    QaiDataProvider<StockEntity> provideStockEntityProvider() {
        QaiDataProvider<StockEntity> provider = new MapDataProvider(STOCK_ENTITIES, hazelcastInstance);
        return provider;
    }

    @Provides
    QaiDataProvider<StockQuote> provideStockQuoteProvider() {
        QaiDataProvider<StockQuote> provider = new MapDataProvider(STOCK_QUOTES, hazelcastInstance);
        return provider;
    }

    @Provides
    QaiDataProvider<WikiArticle> provideWikiArticleData() {
        QaiDataProvider<WikiArticle> provider = new MapDataProvider(WIKIPEDIA, hazelcastInstance);
        return provider;
    }

    @Provides
    @Named("WikiResources_en")
    QaiDataProvider<ResourceData> provideWikiResourceProvider() {
        QaiDataProvider<ResourceData> provider = new MapDataProvider(WIKIPEDIA_RESOURCES, hazelcastInstance);
        return provider;
    }

    @Provides
    @Named("PdfFileResources")
    QaiDataProvider<ResourceData> providePdfResourceProvider() {
        QaiDataProvider<ResourceData> provider = new MapDataProvider(PDF_FILE_RESOURCES, hazelcastInstance);
        return provider;
    }

    @Provides
    @Singleton
    HazelcastInstance provideHazelcastInstance() {

        if (hazelcastInstance != null) {
            return hazelcastInstance;
        }

        ClientConfig clientConfig = new ClientConfig();
        //clientConfig.setInstanceName(QAI_NODE_NAME);
        clientConfig.getNetworkConfig().addAddress(QAI_NODE_TO_CONNECT);

        hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

        return hazelcastInstance;
    }

    @Provides
    @Named("Users")
    @Singleton
    SearchServiceInterface provideUserSearchService() {

        userSearchService = new DistributedSearchService(USERS);
        ((DistributedSearchService) userSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) userSearchService).setResultSink(wikiSearchSink);
        ((DistributedSearchService) userSearchService).initialize();

        return userSearchService;
    }

    @Provides
    @Named("Wikipedia_en")
    @Singleton
    public SearchServiceInterface provideWikipediaSearchService() {

        wikipediaSearchService = new DistributedSearchService(WIKIPEDIA);

        ((DistributedSearchService) wikipediaSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) wikipediaSearchService).setResultSink(wikiSearchSink);
        ((DistributedSearchService) wikipediaSearchService).initialize();

        return wikipediaSearchService;
    }

    @Provides
    @Named("Wiktionary_en")
    @Singleton
    public SearchServiceInterface provideWiktionarySearchService() {

        wiktionarySearchService = new DistributedSearchService(WIKTIONARY);

        ((DistributedSearchService) wiktionarySearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) wiktionarySearchService).setResultSink(wikiSearchSink);
        ((DistributedSearchService) wiktionarySearchService).initialize();

        return wiktionarySearchService;
    }

    @Provides
    @Named("Stock_Groups")
    @Singleton
    SearchServiceInterface provideStockGroupssSearchService() {

        stockGroupsSearchService = new DistributedSearchService(STOCK_GROUPS);

        ((DistributedSearchService) stockGroupsSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) stockGroupsSearchService).setResultSink(financeResultSink);
        ((DistributedSearchService) stockGroupsSearchService).initialize();

        return stockGroupsSearchService;
    }

    @Provides
    @Named("Stock_Quotes")
    @Singleton
    SearchServiceInterface provideStockQuotessSearchService() {

        stockQuotesSearchService = new DistributedSearchService(STOCK_QUOTES);

        ((DistributedSearchService) stockQuotesSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) stockQuotesSearchService).setResultSink(financeResultSink);
        ((DistributedSearchService) stockQuotesSearchService).initialize();

        return stockQuotesSearchService;
    }

    @Provides
    @Named("Stock_Entities")
    @Singleton
    SearchServiceInterface provideStockEntitiesSearchService() {

        stockEntitiesSearchService = new DistributedSearchService(STOCK_ENTITIES);

        ((DistributedSearchService) stockEntitiesSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) stockEntitiesSearchService).setResultSink(financeResultSink);
        ((DistributedSearchService) stockEntitiesSearchService).initialize();

        return stockEntitiesSearchService;
    }

    @Provides
    @Named("Procedures")
    @Singleton
    SearchServiceInterface provideProceduresSearchService() {

        proceduresSearchService = new DistributedSearchService(PROCEDURES);

        ((DistributedSearchService) proceduresSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) proceduresSearchService).setResultSink(procedureResultSink);
        ((DistributedSearchService) proceduresSearchService).initialize();

        return proceduresSearchService;
    }

    @Provides
    @Named("WikiResources_en")
    @Singleton
    SearchServiceInterface provideWikiResourcesSearchService() {

        wikiResourcesSearchService = new DistributedSearchService(WIKIPEDIA_RESOURCES);

        ((DistributedSearchService) wikiResourcesSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) wikiResourcesSearchService).setResultSink(resourceSearchSink);
        ((DistributedSearchService) wikiResourcesSearchService).initialize();

        return wikiResourcesSearchService;
    }

    @Provides
    @Named("MolecularResources")
    @Singleton
    SearchServiceInterface provideMolecularSearchService() {

        molecularResourcesService = new DistributedSearchService(MOLECULAR_RESOURCES);

        ((DistributedSearchService) molecularResourcesService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) molecularResourcesService).setResultSink(resourceSearchSink);
        ((DistributedSearchService) molecularResourcesService).initialize();

        return molecularResourcesService;
    }

    @Provides
    @Named("PdfFileResources")
    @Singleton
    SearchServiceInterface providePdfFileSearchService() {

        pdfFileRsourcesService = new DistributedSearchService(PDF_FILE_RESOURCES);

        ((DistributedSearchService) pdfFileRsourcesService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) pdfFileRsourcesService).setResultSink(resourceSearchSink);
        ((DistributedSearchService) pdfFileRsourcesService).initialize();

        return pdfFileRsourcesService;
    }

    public SearchServiceInterface getNamedService(String name) {

        if (namedSearchServices == null || namedSearchServices.isEmpty()) {
            initKnownNamedServers();
        }

        return namedSearchServices.get(name);
    }

    /**
     * when a new service is created, use this to be able to use it
     *
     * @param name
     * @param service
     */
    public void addNamedSearchService(String name, SearchServiceInterface service) {

        if (namedSearchServices == null || namedSearchServices.isEmpty()) {
            initKnownNamedServers();
        }

        namedSearchServices.put(name, service);
    }

    public Set<String> getSearchServiceNames() {

        initKnownNamedServers();

        return namedSearchServices.keySet();
    }

    private void initKnownNamedServers() {

        if (namedSearchServices == null) {
            namedSearchServices = new HashMap<>();
        }

        if (wikipediaSearchService == null) {
            wikipediaSearchService = provideWikipediaSearchService();
            logger.info("Started service: " + WIKIPEDIA);
            namedSearchServices.put(WIKIPEDIA, wikipediaSearchService);
        }

        if (wiktionarySearchService == null) {
            wiktionarySearchService = provideWiktionarySearchService();
            logger.info("Started service: " + WIKTIONARY);
            namedSearchServices.put(WIKTIONARY, wiktionarySearchService);
        }

        if (wikiResourcesSearchService == null) {
            wikiResourcesSearchService = provideWikiResourcesSearchService();
            logger.info("Started service: " + WIKIPEDIA_RESOURCES);
            namedSearchServices.put(WIKIPEDIA_RESOURCES, wikiResourcesSearchService);
        }

        if (stockEntitiesSearchService == null) {
            stockEntitiesSearchService = provideStockEntitiesSearchService();
            logger.info("Started service: " + STOCK_ENTITIES);
            namedSearchServices.put(STOCK_ENTITIES, stockEntitiesSearchService);
        }

        if (stockGroupsSearchService == null) {
            stockGroupsSearchService = provideStockGroupssSearchService();
            logger.info("Started service: " + STOCK_GROUPS);
            namedSearchServices.put(STOCK_GROUPS, stockGroupsSearchService);
        }

        if (proceduresSearchService == null) {
            proceduresSearchService = provideProceduresSearchService();
            logger.info("Started service: " + PROCEDURES);
            namedSearchServices.put(PROCEDURES, proceduresSearchService);
        }

        if (molecularResourcesService == null) {
            molecularResourcesService = provideMolecularSearchService();
            logger.info("Started service: " + MOLECULAR_RESOURCES);
            namedSearchServices.put(MOLECULAR_RESOURCES, molecularResourcesService);
        }

        if (pdfFileRsourcesService == null) {
            pdfFileRsourcesService = providePdfFileSearchService();
            logger.info("Started service: " + PDF_FILE_RESOURCES);
            namedSearchServices.put(PDF_FILE_RESOURCES, pdfFileRsourcesService);
        }

    }

    private HazelcastInstance getHazelcastInstance() {

        if (hazelcastInstance == null) {
            hazelcastInstance = provideHazelcastInstance();
        }

        return hazelcastInstance;
    }

    public UserManagerInterface getUserManager() {
        if (userManager == null) {
            userManager = provideUserManager();
        }

        return userManager;
    }
}
