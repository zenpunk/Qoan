package qube.qoan.gui.components.workspace.procedure;

import com.google.inject.Injector;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.analysis.NeuralNetworkAnalysis;
import qube.qai.services.ProcedureSourceInterface;
import qube.qoan.QoanUI;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureMenu extends Panel {

    @Inject
    private ProcedureSourceInterface procedureSource;

    /**
     * this is the container for elements which will be
     * needed to design, run and gain access to results
     * of the procedures which have already run
     * For the moment being though, it will be limited only
     * to display the procedure and its results
     */
    public ProcedureMenu() {

        super();

        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
        injector.injectMembers(this);

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("300px");

        ProcedureRepositoryPanel repositoryPanel = new ProcedureRepositoryPanel();
        layout.addComponent(repositoryPanel);

        ProcedureListPanel procedureListPanel = new ProcedureListPanel();
        // create the button which does the work
        Button showSelectedProcedureButton = new Button("Show selected procedure");
        showSelectedProcedureButton.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String name = repositoryPanel.getSelectedProcedure();
                Procedure procedure = null;
                // @TODO this is perhaps really only temporary
                // this is what we do when we need to create a dummy procedure
                if (StringUtils.containsIgnoreCase(name, "dummy")) {
                    procedure = NeuralNetworkAnalysis.Factory.constructProcedure();
                } else {
                    procedure = procedureSource.getProcedureWithName(name);
                }
                procedureListPanel.displayProcedure(procedure);
            }
        });
        showSelectedProcedureButton.setStyleName("link");
        layout.addComponent(showSelectedProcedureButton);
        layout.addComponent(procedureListPanel);
        setContent(layout);

    }

}
