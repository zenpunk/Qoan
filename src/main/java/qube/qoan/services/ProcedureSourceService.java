package qube.qoan.services;

import qube.qai.procedure.Procedure;
import qube.qai.procedure.analysis.*;
import qube.qoan.gui.interfaces.ProcedureSource;

/**
 * Created by rainbird on 12/5/15.
 */
public class ProcedureSourceService implements ProcedureSource {

    @Override
    public Procedure getSelectedProcedure() {
        return NeuralNetworkAnalysis.Factory.constructProcedure();
    }

    @Override
    public Procedure getProcedureWithName(String name) {

        Procedure procedure = null;

        if (ChangePointAnalysis.NAME.equals(name)) {
            procedure = new ChangePointAnalysis();
        } else if (MatrixStatistics.NAME.equals(name)) {
            procedure = new MatrixStatistics();
        } else if (NetworkStatistics.NAME.equals(name)) {
            procedure = new NetworkStatistics();
        } else if (NeuralNetworkAnalysis.NAME.equals(name)) {
            procedure = new NeuralNetworkAnalysis();
        } else if (NeuralNetworkForwardPropagation.NAME.equals(name)) {
            procedure = new NeuralNetworkForwardPropagation();
        } else if (SortingPercentilesProcedure.NAME.equals(name)) {
            procedure = new SortingPercentilesProcedure();
        } else if (TimeSeriesAnalysis.NAME.equals(name)) {
            procedure = new TimeSeriesAnalysis();
        }

        return procedure;
    }
}
