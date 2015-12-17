package qube.qoan.gui.components.workspace.procedure;

import com.google.inject.Injector;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.analysis.NeuralNetworkAnalysis;
import qube.qai.services.ProcedureSource;
import qube.qoan.QoanUI;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureRepositoryPanel extends Panel {

    @Inject
    private ProcedureSource procedureSource;
    private String dummyName = "Dummy neural-network analysis";

    private ObjectProperty<String> name;
    /**
     * this is supposed to read the existing and already
     * defined procedure templates and return the selected
     * one among them to be displayed
     */
    public ProcedureRepositoryPanel() {

        super();

        // @TODO is there a way to get rid of this?
        Injector injector = QoanUI.getInjector();
        injector.injectMembers(this);

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();

        Label label = new Label("Procedures in repository");
        layout.addComponent(label);

        // for the time being only one procedure is for selection
        ComboBox procedureSelectBox = new ComboBox("Procedures");
        name = new ObjectProperty<String>("", String.class);
        procedureSelectBox.setPropertyDataSource(name);
        procedureSelectBox.addItem(dummyName);
        String[] procedureNames = procedureSource.getProcedureNames();
        for (String name : procedureNames) {
            procedureSelectBox.addItem(name);
        }

        layout.addComponent(procedureSelectBox);

        setContent(layout);
    }

    public String getSelectedProcedure() {
//        NeuralNetworkAnalysis neuralNetworkAnalysis = (NeuralNetworkAnalysis) NeuralNetworkAnalysis.Factory.constructProcedure();
//        return neuralNetworkAnalysis;
        return name.getValue();
    }

//    @Override
//    public Procedure getProcedureWithName(String name) {
//        return procedureSource.getProcedureWithName(name);
//    }
}
