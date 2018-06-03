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
import org.qai.services.ProcedureRunnerInterface;
import org.qai.services.UUIDServiceInterface;
import org.qai.services.implementation.ProcedureRunner;
import org.qai.services.implementation.UUIDService;
import org.qoan.authentication.UserManagerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Properties;

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

    private ClientConfig config;

    private HazelcastInstance hazelcastInstance;

    private UserManagerInterface userManager;

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

    public QoanModule(Properties properties, ClientConfig config) {
        this.properties = properties;
        this.config = config;
    }

    @Override
    protected void configure() {

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
    HazelcastInstance provideHazelcastInstance() {

        if (hazelcastInstance != null) {
            return hazelcastInstance;
        }

        //ClientConfig config = new ClientConfig();
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

    private HazelcastInstance getHazelcastInstance() {

        if (hazelcastInstance == null) {
            hazelcastInstance = provideHazelcastInstance();
        }

        return hazelcastInstance;
    }

}
