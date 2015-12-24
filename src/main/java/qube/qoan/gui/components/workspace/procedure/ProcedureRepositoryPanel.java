package qube.qoan.gui.components.workspace.procedure;

import com.google.inject.Injector;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.*;
import qube.qai.services.ProcedureSourceInterface;
import qube.qoan.QoanUI;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureRepositoryPanel extends Panel {

    @Inject
    private ProcedureSourceInterface procedureSource;
    private String dummyName = "Dummy neural-network analysis";

    private ObjectProperty<String> name;
    /**
     * this is supposed to read the existing and already
     * defined procedure templates and return the selected
     * one among them to be displayed
     */
    public ProcedureRepositoryPanel() {

        super();

        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
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
