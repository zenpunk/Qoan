package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.data.Item;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import qube.qai.procedure.Procedure;
import qube.qai.procedure.ProcedureVisitor;

import java.io.Serializable;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureListPanel extends Panel {

    private Tree procedureTree;

    private Accordion procedureAccordion;

    /**
     * this panel is mainly for displaying process settings
     * and managing those, obviously, when new procedures are
     * being created.
     */
    public ProcedureListPanel() {

        super();
        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();
        Label nameLabel = new Label("Selected Procedures:");
        nameLabel.setStyleName("bold");
        layout.addComponent(nameLabel);

        procedureAccordion = new Accordion();
        procedureAccordion.setSizeFull();

        layout.addComponent(procedureAccordion);

        setContent(layout);
    }

    public void displayProcedure(Procedure procedure) {
        String name = procedure.getName();

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        // put a description of the procedure
        Label descriptionLabel = new Label(procedure.getDescription());
        layout.addComponent(descriptionLabel);

        procedureTree = new Tree("Procedure: " + name);
        procedureTree.setImmediate(true);
        procedureTree.setSelectable(true);
        procedureTree.setDragMode(Tree.TreeDragMode.NODE);
        procedureTree.addContainerProperty("Name", String.class, "Name");
        procedureTree.addContainerProperty("UUID", String.class, "UUID");

        // use our smart-visitor for building the tree
        ProcedureTreeBuilder treeBuilder = new ProcedureTreeBuilder();
        treeBuilder.visit(procedure, name);
        layout.addComponent(procedureTree);

        Panel panel = new Panel(layout);
        procedureAccordion.addTab(panel).setCaption(name);
    }

    class ProcedureTreeBuilder implements ProcedureVisitor {

        @Override
        public Object visit(Procedure procedure, Object parent) {

            String name = procedure.getName();
            ProcedureItem current = new ProcedureItem(procedure.getUuid(), procedure.getName());
            procedureTree.addItem(current);
            procedureTree.setParent(current, parent);

            procedure.childrenAccept(this, current);

            return name;
        }
    }

    public class ProcedureItem implements Serializable {
        private String uuid;
        private String name;

        public ProcedureItem(String uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ProcedureItem) {
                ProcedureItem other = (ProcedureItem) obj;
                return new EqualsBuilder().
                        append(name, other.name).
                        isEquals();
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(name).hashCode();
        }
    }
}
