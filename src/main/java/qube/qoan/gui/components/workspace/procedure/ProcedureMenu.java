package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qai.procedure.Procedure;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureMenu extends Panel {

    /**
     * this is the container for elements which will be
     * needed to design, run and gain access to results
     * of the procedures which have already run
     * For the moment being though, it will be limited only
     * to display the procedure and its results
     */
    public ProcedureMenu() {

        super();

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();

        ProcedureRepositoryPanel repositoryPanel = new ProcedureRepositoryPanel();
        repositoryPanel.setWidth("100%");
        layout.addComponent(repositoryPanel);

        ProcedureListPanel procedurePanel = new ProcedureListPanel();
        procedurePanel.setWidth("100%");

        Button showSelectedProcedureButton = new Button("Show selected procedure");
        showSelectedProcedureButton.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Procedure procedure = repositoryPanel.getSelectedProcedure();
                procedurePanel.displayProcedure(procedure);
            }
        });
//        showSelectedProcedureButton.setStyleName("link");
        layout.addComponent(showSelectedProcedureButton);
        layout.addComponent(procedurePanel);
        setContent(layout);

    }

}
