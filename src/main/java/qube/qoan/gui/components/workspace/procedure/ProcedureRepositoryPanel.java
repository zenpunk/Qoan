package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.analysis.NeuralNetworkAnalysis;
import qube.qoan.gui.interfaces.ProcedureSource;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureRepositoryPanel extends Panel implements ProcedureSource {

    /**
     * this is supposed to read the existing and already
     * defined procedure templates and return the selected
     * one among them to be displayed
     */
    public ProcedureRepositoryPanel() {

        super();

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();

        Label label = new Label("Procedures in repository");
        layout.addComponent(label);

        // for the time being only one procedure is for selection
        ObjectProperty<String> name = new ObjectProperty<String>("", String.class);
        ComboBox procedureSelectBox = new ComboBox("Procedures");
        procedureSelectBox.addItem("Neural-Network Analysis Procedure");
        layout.addComponent(procedureSelectBox);

        setContent(layout);
    }

    @Override
    public Procedure getSelectedProcedure() {
        NeuralNetworkAnalysis neuralNetworkAnalysis = (NeuralNetworkAnalysis) NeuralNetworkAnalysis.Factory.constructProcedure();
        return neuralNetworkAnalysis;
    }
}
