package qube.qoan.gui.interfaces;

import qube.qai.procedure.Procedure;

/**
 * Created by rainbird on 12/2/15.
 */
public interface ProcedureSource {

    Procedure getSelectedProcedure();

    Procedure getProcedureWithName(String name);

}
