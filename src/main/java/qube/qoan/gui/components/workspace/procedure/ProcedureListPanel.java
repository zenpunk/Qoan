/*
 * Copyright 2017 Qoan Software Association. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package qube.qoan.gui.components.workspace.procedure;

import com.vaadin.ui.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import qube.qai.data.DataVisitor;
import qube.qai.network.Network;
import qube.qai.persistence.StockEntity;
import qube.qai.persistence.StockQuote;
import qube.qai.persistence.WikiArticle;
import qube.qai.procedure.Procedure;

import java.io.Serializable;

/**
 * Created by rainbird on 12/2/15.
 */
public class ProcedureListPanel extends Panel {

    private Tree currentTree;

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
        String name = procedure.getName().getName();

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        // put a description of the procedure
        Label descriptionLabel = new Label(procedure.getDescription());
        layout.addComponent(descriptionLabel);

        currentTree = new Tree("Procedure: " + name);
        currentTree.setImmediate(true);
        currentTree.setSelectable(true);
        currentTree.setDragMode(Tree.TreeDragMode.NODE);
        currentTree.addContainerProperty("Name", String.class, "Name");
        currentTree.addContainerProperty("UUID", String.class, "UUID");

        // use our smart-visitor for building the tree
        ProcedureTreeBuilder treeBuilder = new ProcedureTreeBuilder();
        procedure.accept(treeBuilder, null);
        layout.addComponent(currentTree);

        Panel panel = new Panel(layout);
        procedureAccordion.addTab(panel).setCaption(name);
    }

    class ProcedureTreeBuilder implements DataVisitor {

        @Override
        public Object visit(StockEntity visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(StockQuote visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(WikiArticle visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(Network visitee, Object data) {
            return data;
        }

        @Override
        public Object visit(Procedure visitee, Object parent) {
            if (parent == null) {
                parent = visitee.getName();
            }
            ProcedureItem current = new ProcedureItem(visitee.getUuid(), visitee.getName().getName());
            currentTree.addItem(current);
            currentTree.setParent(current, parent);
            return current;
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
