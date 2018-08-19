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

package org.qoan.services.implementation;

import com.hazelcast.core.HazelcastInstance;
import org.qai.main.QaiServerModule;
import org.qai.services.DistributedSearchServiceInterface;
import org.qoan.services.QoanTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * Created by rainbird on 1/6/16.
 */
public class QaiIntegrationSearchServicesTest extends QoanTestBase {

    private Logger logger = LoggerFactory.getLogger("QaiIntegrationTestModule");

    private String propertiesFileNmae = "org/qoan/services/config_dev.properties";

    private String prop_contexts = "search_services.contexts";

    private Properties properties;

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Inject
    private Map<String, DistributedSearchServiceInterface> searchServices;

    @Override
    protected void setUp() throws Exception {
        properties = new Properties();

        try {
            ClassLoader loader = QaiServerModule.class.getClassLoader();
            URL url = loader.getResource(propertiesFileNmae);
            properties.load(url.openStream());

        } catch (IOException e) {
            log("Error while loading configuration file: " + propertiesFileNmae + " with exception: " + e);
            throw new RuntimeException("Configuration file: '" + propertiesFileNmae + "' could not be found- have to exit!");
        }
    }

    public void testDistributedUserSearch() throws Exception {

        assertNotNull("configuration error, there is not searchServices map", searchServices);
        assertNotNull("configuration error, there is no hazelcastInstance", hazelcastInstance);
        assertNotNull("configuration error, there is no properties", properties);

        String allContexts = properties.getProperty(prop_contexts);
        String[] contexts = allContexts.split(",", 100);
        log("found contexts:");
        log(Arrays.toString(contexts));


        for (int i = 0; i < contexts.length; i++) {
            DistributedSearchServiceInterface searchService = searchServices.get(contexts[i]);
            assertNotNull("there has to be a search service corresponding to: " + contexts[i], searchService);
        }

    }


    private void log(String message) {
        System.out.println(message);
    }
}
