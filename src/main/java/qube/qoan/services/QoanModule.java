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
import com.google.inject.name.Names;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.DistributedSearchService;
import qube.qoan.authentication.UserManager;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by rainbird on 11/2/15.
 */
//@BindConfig(value = "qube/qoan/services/config_dev", syntax = Syntax.PROPERTIES)
//@BindConfig(value = "qube/qoan/services/config_deploy", syntax = Syntax.PROPERTIES)
public class QoanModule extends AbstractModule {

    private static Logger logger = LoggerFactory.getLogger("QoanModule");

    public static final String CONFIG_FILE_NAME = "config_dev.properties";

    private boolean isServerQaiNode = true;

    // for the time being we leave it at that
    private static String QAI_NODE_NAME = "QaiNode";
    private HazelcastInstance hazelcastInstance;
    private UserManager userManager;

    //@InjectConfig(value = "QAI_NODE_TO_CONNECT")
    public String qaiNodeToConnect;

    @Override
    protected void configure() {

        //install(ConfigurationModule.create());
        //requestInjection(this);

        try {
            Properties properties = new Properties();
            properties.load(new FileReader(CONFIG_FILE_NAME));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            logger.error("Error while loading configuration file: " + CONFIG_FILE_NAME, e);
        }

    }

    @Provides
    @Singleton
    ProcedureCache provideProcedureSource() {
        return new ProcedureCache();
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
    @Singleton
    HazelcastInstance provideHazelcastInstance() {

        if (hazelcastInstance != null) {
            return hazelcastInstance;
        }

        ClientConfig clientConfig = new ClientConfig();
        //clientConfig.setInstanceName(QAI_NODE_NAME);
        clientConfig.getNetworkConfig().addAddress(qaiNodeToConnect);

        hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

        return hazelcastInstance;
    }

    @Provides
    @Named("Users")
    @Singleton
    SearchServiceInterface provideUserSearchService() {

        DistributedSearchService distributedSearch = new DistributedSearchService("Users");
        distributedSearch.setHazelcastInstance(getHazelcastInstance());
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("Wikipedia_en")
    @Singleton
    SearchServiceInterface provideWikipediaSearchService() {

        DistributedSearchService distributedSearch = new DistributedSearchService("Wikipedia_en");

        distributedSearch.setHazelcastInstance(getHazelcastInstance());
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("Wiktionary_en")
    @Singleton
    SearchServiceInterface provideWiktionarySearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Wiktionary_en");

        distributedSearch.setHazelcastInstance(getHazelcastInstance());
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("WikiResources_en")
    @Singleton
    SearchServiceInterface provideWikiResourcesSearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("WikiResources_en");

        distributedSearch.setHazelcastInstance(getHazelcastInstance());
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("Stock_Entities")
    @Singleton
    SearchServiceInterface provideStockEntitiesSearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Stock_Entities");

        distributedSearch.setHazelcastInstance(getHazelcastInstance());
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("Procedures")
    @Singleton
    SearchServiceInterface provideProceduresSearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Procedures");

        distributedSearch.setHazelcastInstance(getHazelcastInstance());
        distributedSearch.initialize();

        return distributedSearch;
    }

    private HazelcastInstance getHazelcastInstance() {

        if (hazelcastInstance == null) {
            hazelcastInstance = provideHazelcastInstance();
        }

        return hazelcastInstance;
    }
}
