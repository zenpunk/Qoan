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
import net.jmob.guice.conf.core.BindConfig;
import net.jmob.guice.conf.core.ConfigurationModule;
import net.jmob.guice.conf.core.InjectConfig;
import net.jmob.guice.conf.core.Syntax;
import qube.qai.persistence.ModelStore;
import qube.qai.services.DataServiceInterface;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.DistributedDataService;
import qube.qai.services.implementation.DistributedSearchService;
import qube.qoan.authentication.UserManager;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by rainbird on 11/2/15.
 */
@BindConfig(value = "qube/qoan/services/config_dev", syntax = Syntax.PROPERTIES)
//@BindConfig(value = "qube/qoan/services/config_deploy", syntax = Syntax.PROPERTIES)
public class QoanModule extends AbstractModule {

    private boolean isServerQaiNode = true;

    // for the time being we leave it at that
    private static String QAI_NODE_NAME = "QaiNode";
    private HazelcastInstance hazelcastInstance;
    private UserManager userManager;

    @InjectConfig(value = "QAI_NODE_TO_CONNECT")
    public String qaiNodeToConnect;

    @Override
    protected void configure() {

        install(ConfigurationModule.create());
        requestInjection(this);

    }

    @Provides
    @Singleton
    @Named("USER")
    DataServiceInterface provideUserDataService() {
        // @TODO add the additional configuraiton of the whole
        return new ModelStore();
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
        //@Named("HAZELCAST_CLIENT")
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
    @Named("Wiktionary_en")
    @Singleton
    SearchServiceInterface provideWiktionarySearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Wiktionary_en");
        distributedSearch.setHazelcastInstance(hazelcastInstance);
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("Wikipedia_en")
    @Singleton
    SearchServiceInterface provideWikipediaSearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Wikipedia_en");
        distributedSearch.setHazelcastInstance(hazelcastInstance);
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("Stock_Quotes")
    @Singleton
    SearchServiceInterface provideStockQuoteSearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Stock_Quotes");
        distributedSearch.setHazelcastInstance(hazelcastInstance);
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides
    @Named("USER")
    @Singleton
    DataServiceInterface provideUserSearchService() {
        DistributedDataService distributedSearch = new DistributedDataService("USER");
        distributedSearch.setHazelcastInstance(hazelcastInstance);
        distributedSearch.initialize();

        return distributedSearch;
    }
}
