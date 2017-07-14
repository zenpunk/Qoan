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

package qube.qoan.gui.components.common.decorators;

import com.hazelcast.core.HazelcastInstance;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.search.SearchResultPanel;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainbird on 7/4/17.
 */
public class BaseDecorator extends Panel implements Decorator {

    @Inject
    protected HazelcastInstance hazelcastInstance;

    protected Map<String, Decorator> decoratorMap;

    private TabSheet tabSheet;

    public BaseDecorator() {
        decoratorMap = new HashMap<>();
        tabSheet = new TabSheet();
    }

    @Override
    public void decorate(SearchResult toDecorate) {
        SearchResultPanel resultPanel = new SearchResultPanel(toDecorate);
        tabSheet.addTab(resultPanel, "Search result");
    }

    public void addDecorator(String name, Decorator decorator) {
        decoratorMap.put(name, decorator);
    }

    @Override
    public void decorateAll(SearchResult searchResult) {
        for (String decoratorName : decoratorMap.keySet()) {
            Decorator decorator = decoratorMap.get(decoratorName);
            decorator.decorate(searchResult);
            tabSheet.addTab(decorator).setCaption(decoratorName);
        }
        decorate(searchResult);
    }
}
