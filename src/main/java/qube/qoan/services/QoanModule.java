package qube.qoan.services;

import com.google.inject.AbstractModule;
import qube.qoan.gui.interfaces.ProcedureSource;

/**
 * Created by rainbird on 11/2/15.
 */
public class QoanModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(ProcedureSource.class).to(ProcedureSourceService.class);

    }
}
