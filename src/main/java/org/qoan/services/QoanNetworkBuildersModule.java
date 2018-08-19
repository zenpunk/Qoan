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
import org.qai.main.QaiConstants;
import org.qai.services.DistributedNetworkBuilderInterface;
import org.qai.services.implementation.DistributedNetworkBuilder;

import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

public class QoanNetworkBuildersModule extends AbstractModule implements QaiConstants {

    private String network_builders = "network_builders.contexts";

    private Properties properties;

    private Map<String, DistributedNetworkBuilderInterface> networkBuilders;

    public QoanNetworkBuildersModule(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {

        String contexts = properties.getProperty(network_builders);
        StringTokenizer tokenizer = new StringTokenizer(contexts, ",");
        while (tokenizer.hasMoreTokens()) {
            String context = tokenizer.nextToken();
            DistributedNetworkBuilderInterface networkBuilder = new DistributedNetworkBuilder(context);
            provideNetworkBuilders().put(context, networkBuilder);
        }
    }

    private Map<String, DistributedNetworkBuilderInterface> provideNetworkBuilders() {

        return null;
    }


}
