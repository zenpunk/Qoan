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
import com.hazelcast.core.IMap;
import org.qai.main.QaiConstants;
import org.qai.persistence.DataProvider;
import org.qai.persistence.QaiDataProvider;
import org.qai.persistence.StockEntity;
import org.qai.persistence.StockGroup;
import org.qai.procedure.Procedure;
import org.qai.procedure.ProcedureLibrary;
import org.qai.procedure.utils.SelectForAll;
import org.qai.procedure.utils.SelectForEach;
import org.qai.services.ProcedureRunnerInterface;
import org.qoan.services.QoanTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProcedureLibraryTests extends QoanTestBase {

    private Logger logger = LoggerFactory.getLogger("WikiRipperProcedureTest");

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Inject
    private ProcedureRunnerInterface procedureRunner;

    public void testWikiNetworkBuilder() throws Exception {

        SelectForAll procedure = ProcedureLibrary.wikiNetworkBuilderTemplate.createProcedure();
        assertNotNull("duh!", procedure);

        // @TODO the procedure template as well as the test itself is missing
        fail("there has to be a template which would be returning this procedure ready to use too");

    }

    public void testFinanceNetworkBuilderTemplate() throws Exception {

        SelectForAll procedure = ProcedureLibrary.financeNetworkBuilderTemplate.createProcedure();
        assertNotNull("duh!", procedure);

        Set<StockEntity> pickedEntities = pickRandomFrom(10);
        assertNotNull("entities must have been initialized", pickedEntities);
        assertTrue("there has to be a stock entity", !pickedEntities.isEmpty());

        Collection<QaiDataProvider> results = convert2SearchResults(pickedEntities);
        procedure.setInputs(results);

    }

    public void testChangePointAnalysis() throws Exception {

        SelectForEach procedure = ProcedureLibrary.changePointAnalysisTemplate.createProcedure();
        assertNotNull("duh!", procedure);

        Set<StockEntity> pickedEntities = pickRandomFrom(1);
        assertNotNull("entities must have been initialized", pickedEntities);
        assertTrue("there has to be a stock entity", !pickedEntities.isEmpty());

        Collection<QaiDataProvider> results = convert2SearchResults(pickedEntities);
        procedure.setInputs(results);

        procedureRunner.submitProcedure(procedure);

        IMap<String, Procedure> procedureMap = hazelcastInstance.getMap(QaiConstants.PROCEDURES);
        Procedure copy = procedureMap.get(procedure.getUuid());
        assertNotNull("there has to be a copy and all that", copy);
        //Collection markers = ((ChangePoints) copy).getMarkers();
        //assertNotNull("if the procedure has actually been executed and save there have to be markers", markers);
    }

    public void testStockQuoteRetrieverTemplate() throws Exception {

        SelectForAll procedure = ProcedureLibrary.stockQuoteUpdaterTemplate.createProcedure();
        assertNotNull("duh!", procedure);

        Set<StockEntity> pickedEntities = pickRandomFrom(5);
        assertNotNull("entities must have been initialized", pickedEntities);
        assertTrue("there has to be entities", !pickedEntities.isEmpty());

        Collection<QaiDataProvider> results = convert2SearchResults(pickedEntities);

        procedure.setInputs(results);

        procedureRunner.submitProcedure(procedure);
        // and hope all has gone well, i suppose
        String names = "";
        for (StockEntity entity : pickedEntities) {
            names += entity.getTickerSymbol() + " ";
        }

        log("Have successfully submitted update procedure for " + names);
    }

    private Collection<QaiDataProvider> convert2SearchResults(Set<StockEntity> entities) {
        Collection<QaiDataProvider> results = new ArrayList<>();

        for (StockEntity entity : entities) {
            results.add(new DataProvider(entity));
        }

        return results;
    }

    private Set<StockEntity> pickRandomFrom(int size) {
        Set<StockEntity> picked = new HashSet<>();
        IMap<String, StockGroup> groupMap = hazelcastInstance.getMap(QaiConstants.STOCK_GROUPS);
        assertTrue("there has to be some groups", !groupMap.keySet().isEmpty());

        for (String uuid : groupMap.keySet()) {
            StockGroup group = groupMap.get(uuid);
            // check whether the group has entities and delete it if not

            if (group.getEntities().isEmpty()) {
                groupMap.delete(group);
                String message = String.format("StockGroup '%s' has no entities- deleting", group.getName());
                log(message);
            }

            for (StockEntity entity : group.getEntities()) {
                picked.add(entity);
                if (picked.size() >= size) {
                    break;
                }
            }

            break;
        }

        return picked;
    }

    private void log(String message) {
        logger.info(message);
    }
}