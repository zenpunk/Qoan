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

package qube.qoan.services;

import qube.qai.data.DataVisitor;
import qube.qai.network.Network;
import qube.qai.persistence.StockEntity;
import qube.qai.persistence.StockQuote;
import qube.qai.persistence.WikiArticle;
import qube.qai.procedure.Procedure;
import qube.qai.services.ProcedureSourceInterface;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by rainbird on 12/27/15.
 */
public class ProcedureCache implements ProcedureSourceInterface {

    private Map<String, Procedure> procedureMap;

    public ProcedureCache() {
        procedureMap = new TreeMap<String, Procedure>();
    }

    public void cacheProcedure(Procedure procedure) {
        procedureMap.put(procedure.getUuid(), procedure);

        ProcedureUUIDCollector treeBuilder = new ProcedureUUIDCollector();
        procedure.accept(treeBuilder, null);
    }

    public Procedure getProcedureWithName(String name) {
        return procedureMap.get(name);
    }

    public String[] getProcedureNames() {
        return new String[0];
    }

    class ProcedureUUIDCollector implements DataVisitor {

        public Object visit(StockEntity visitee, Object data) {
            return data;
        }

        public Object visit(StockQuote visitee, Object data) {
            return data;
        }

        public Object visit(WikiArticle visitee, Object data) {
            return data;
        }

        public Object visit(Network visitee, Object data) {
            return data;
        }

        public Object visit(Procedure visitee, Object parent) {
            if (!procedureMap.containsKey(visitee.getUuid())) {
                procedureMap.put(visitee.getUuid(), visitee);
            }
            return null;
        }
    }
}
