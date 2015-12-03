package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.data.Item;
import com.vaadin.ui.*;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.ProcedureVisitor;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedurePanel extends Panel {

    private Tree procedureTree;

    private Accordion procedureAccordion;

    /**
     * this panel is mainly for displaying process settings
     * and managing those, obviously, when new procedures are
     * being created.
     */
    public ProcedurePanel() {

        super();

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();

        Label nameLabel = new Label("Procedures:");
        layout.addComponent(nameLabel);

        procedureAccordion = new Accordion();
        procedureAccordion.setSizeFull();

        layout.addComponent(procedureAccordion);

        setContent(layout);
    }

    public void displayProcedure(Procedure procedure) {
        String name = procedure.getName();

        VerticalLayout layout = new VerticalLayout();

//        Label nameLabel = new Label(name);
//        layout.addComponent(nameLabel);

        Label descriptionLabel = new Label(procedure.getDescription());
        layout.addComponent(descriptionLabel);

        procedureTree = new Tree("Procedure: " + name);
        //Item parent = procedureTree.addItem(name);
        ProcedureTreeBuilder treeBuilder = new ProcedureTreeBuilder();
        treeBuilder.visit(procedure, name);
        layout.addComponent(procedureTree);

        Panel panel = new Panel(layout);
        procedureAccordion.addTab(panel).setCaption(name);
    }

    class ProcedureTreeBuilder implements ProcedureVisitor {

        @Override
        public Object visit(Procedure procedure, Object data) {

            String parent = (String) data;
            String name = procedure.getName();
            procedureTree.addItem(name);
            procedureTree.setParent(name, parent);

            procedure.childrenAccept(this, name);

            return name;
        }
    }
}
