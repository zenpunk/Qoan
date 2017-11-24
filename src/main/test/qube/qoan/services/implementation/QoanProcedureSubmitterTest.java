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

package qube.qoan.services.implementation;

import qube.qai.procedure.ProcedureConstants;
import qube.qai.procedure.ProcedureLibrary;
import qube.qai.procedure.analysis.ChangePointAnalysis;
import qube.qai.procedure.analysis.MarketNetworkBuilder;
import qube.qai.procedure.analysis.SortingPercentilesProcedure;
import qube.qai.procedure.finance.StockQuoteRetriever;
import qube.qoan.services.ProcedureSubmitterInterface;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

public class QoanProcedureSubmitterTest extends QoanTestBase {

    @Inject
    protected ProcedureSubmitterInterface procedureSubmitter;

    public void testProcedureExceution() throws Exception {

        assertNotNull("there has to be an instance of QoanProcedureSubmitter", procedureSubmitter);

        //ProcedureTemplate[] templates = ProcedureLibrary.allTemplates;

        //-> pick out the data for the list of entities.
        String[] stockSymbols = {"GOOG", "ORCL"};

        //-> divide the data into intervals and train neural-networks.
        MarketNetworkBuilder marketNetwork = ProcedureLibrary.marketNetworkBuilderTemplate.createProcedure();

        //-> run the interval-analysis on the averages.
        ChangePointAnalysis changePoint = ProcedureLibrary.changePointAnalysisTemplate.createProcedure();

        //-> create an average-time series based on them.
        SortingPercentilesProcedure sorter = ProcedureLibrary.sortingPercentilesTemplate.createProcedure();

        // -> collect their data.
        Collection<StockQuoteRetriever> retrievers = new ArrayList<>();
        for (String symbol : stockSymbols) {

            StockQuoteRetriever retriever = ProcedureLibrary.stockQuoteRetriverTemplate.createProcedure();
            retriever.setTickerSymbol(symbol);
            retrievers.add(retriever);
        }

        procedureSubmitter.submitProcedure(marketNetwork);

        ProcedureConstants.ProcedureState state = procedureSubmitter.queryState(marketNetwork.getUuid());
        assertNotNull("there has to be a state for the procedure", state);

    }
}
