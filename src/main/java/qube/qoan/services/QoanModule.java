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
import qube.qai.persistence.MapDataProvider;
import qube.qai.persistence.QaiDataProvider;
import qube.qai.persistence.StockGroup;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.ProcedureRunnerInterface;
import qube.qai.services.ProcedureSourceInterface;
import qube.qai.services.SearchResultSink;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.CachedProcedureSourceService;
import qube.qai.services.implementation.DistributedSearchService;
import qube.qai.services.implementation.ProcedureRunner;
import qube.qoan.authentication.UserManager;
import qube.qoan.gui.components.common.search.FinanceSearchResultSink;
import qube.qoan.gui.components.common.search.ProcedureSearchResultSink;
import qube.qoan.gui.components.common.search.WikiSearchSink;

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

    private UserManager userManager;

    private WikiSearchSink wikiSearchSink;

    private FinanceSearchResultSink financeResultSink;

    private ProcedureSearchResultSink procedureResultSink;

    private SearchServiceInterface userSearchService;
    private SearchServiceInterface wikipediaSearchService;
    private SearchServiceInterface wiktionarySearchService;
    private SearchServiceInterface wikiResourcesSearchService;
    private SearchServiceInterface stockEntitiesSearchService;
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
        procedureResultSink = new ProcedureSearchResultSink();
        return procedureResultSink;
    }

    @Provides
    @Singleton
    @Named("FinanceResults")
    SearchResultSink provideFinanceResultSink() {
        financeResultSink = new FinanceSearchResultSink();
        return financeResultSink;
    }

    @Provides
    @Singleton
    UserManager provideUserManager() {

        if (userManager != null) {
            return userManager;
        }

        userManager = new UserManager();
        return userManager;
    }

    @Provides
    QaiDataProvider<StockGroup> provideStockGroupProvider() {
        QaiDataProvider<StockGroup> provider = new MapDataProvider(STOCK_GROUPS, hazelcastInstance);
        return provider;
    }

    @Provides
    QaiDataProvider<WikiArticle> provideWikiArticleData() {
        QaiDataProvider<WikiArticle> provider = new MapDataProvider(WIKIPEDIA, hazelcastInstance);
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
    @Named("WikiResources_en")
    @Singleton
    SearchServiceInterface provideWikiResourcesSearchService() {

        wikiResourcesSearchService = new DistributedSearchService(WIKIPEDIA_RESOURCES);

        ((DistributedSearchService) wikiResourcesSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) wikiResourcesSearchService).setResultSink(wikiSearchSink);
        ((DistributedSearchService) wikiResourcesSearchService).initialize();

        return wikiResourcesSearchService;
    }

    private SearchServiceInterface stockGroupsSearchService;

    @Provides
    @Named("Stock_Groups")
    @Singleton
    SearchServiceInterface provideStockGroupssSearchService() {

        stockGroupsSearchService = new DistributedSearchService(STOCK_GROUPS);

        ((DistributedSearchService) stockGroupsSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) stockGroupsSearchService).setResultSink(wikiSearchSink);
        ((DistributedSearchService) stockGroupsSearchService).initialize();

        return stockGroupsSearchService;
    }

    @Provides
    @Named("Stock_Entities")
    @Singleton
    SearchServiceInterface provideStockEntitiesSearchService() {

        stockEntitiesSearchService = new DistributedSearchService(STOCK_ENTITIES);

        ((DistributedSearchService) stockEntitiesSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) stockEntitiesSearchService).setResultSink(wikiSearchSink);
        ((DistributedSearchService) stockEntitiesSearchService).initialize();

        return stockEntitiesSearchService;
    }


    @Provides
    @Named("Procedures")
    @Singleton
    SearchServiceInterface provideProceduresSearchService() {

        proceduresSearchService = new DistributedSearchService(PROCEDURES);

        ((DistributedSearchService) proceduresSearchService).setHazelcastInstance(getHazelcastInstance());
        ((DistributedSearchService) proceduresSearchService).setResultSink(wikiSearchSink);
        ((DistributedSearchService) proceduresSearchService).initialize();

        return proceduresSearchService;
    }

    public SearchServiceInterface getNamedService(String name) {

        initKnownNamedServers();

        return namedSearchServices.get(name);
    }

    /**
     * when a new service is created, use this to be able to use it
     *
     * @param name
     * @param service
     */
    public void addNamedSearchService(String name, SearchServiceInterface service) {

        initKnownNamedServers();

        namedSearchServices.put(name, service);
    }

    public Set<String> getSearchServiceNames() {

        initKnownNamedServers();

        return namedSearchServices.keySet();
    }

    private void initKnownNamedServers() {

        if (namedSearchServices == null) {

            namedSearchServices = new HashMap<>();

            namedSearchServices.put(WIKIPEDIA, wikipediaSearchService);
            namedSearchServices.put(WIKTIONARY, wiktionarySearchService);
//            namedSearchServices.put(WIKIPEDIA_RESOURCES, wikiResourcesSearchService);
//            namedSearchServices.put(STOCK_ENTITIES, stockEntitiesSearchService);
//            namedSearchServices.put(STOCK_GROUPS, stockGroupsSearchService);
//            namedSearchServices.put(PROCEDURES, proceduresSearchService);
        }
    }

    private HazelcastInstance getHazelcastInstance() {

        if (hazelcastInstance == null) {
            hazelcastInstance = provideHazelcastInstance();
        }

        return hazelcastInstance;
    }
}
