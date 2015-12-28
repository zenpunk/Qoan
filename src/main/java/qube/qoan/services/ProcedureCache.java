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

    private Map<String,Procedure> procedureMap;

    public ProcedureCache() {
        procedureMap = new TreeMap<>();
    }

    public void cacheProcedure(Procedure procedure) {
        procedureMap.put(procedure.getUuid(), procedure);

        ProcedureUUIDCollector treeBuilder = new ProcedureUUIDCollector();
        procedure.accept(treeBuilder, null);
    }

    @Override
    public Procedure getProcedureWithName(String name) {
        return procedureMap.get(name);
    }

    @Override
    public String[] getProcedureNames() {
        return new String[0];
    }

    class ProcedureUUIDCollector implements DataVisitor {

        @Override
        public Object visit(StockEntity visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(StockQuote visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(WikiArticle visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(Network visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(Procedure visitee, Object parent) {
            if (!procedureMap.containsKey(visitee.getUuid())) {
                procedureMap.put(visitee.getUuid(), visitee);
            }
            return null;
        }
    }
}
