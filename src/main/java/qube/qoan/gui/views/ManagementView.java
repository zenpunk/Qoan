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

package qube.qoan.gui.views;

import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;
import com.vaadin.data.Item;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.services.ProcedureRunnerInterface;

import javax.inject.Inject;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by rainbird on 11/27/15.
 */
public class ManagementView extends QoanView {

    private static Logger logger = LoggerFactory.getLogger("ManagementView");

    public static String NAME = "ManagementView";

//    @Inject
//    private HazelcastInstance hazelcastInstance;

    @Inject
    private ProcedureRunnerInterface procedureRunner;

    protected Set<String> procedureUuids;

    protected Set<String> remotelyCreatedUuids;

//    protected VerticalLayout layout;

//    private boolean initialized = false;

    public ManagementView() {
        this.viewTitle = "Qoan Management";
    }


//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        if (!initialized) {
//            initialize();
//            initialized = true;
//        }
//    }

    /**
     * traditional way for getting the gui done,
     * nothing original really
     */
    protected void initialize() {

        remotelyCreatedUuids = new TreeSet<String>();

//        UI.getCurrent().getPage().setTitle("Qoan Management");

//        layout = new VerticalLayout();
//        layout.setWidth("100%");
//
//        QoanHeader header = new QoanHeader();
//        layout.addComponent(header);

//        final SimpleLoginForm loginForm = new SimpleLoginForm();
//        loginForm.clear(); // should work even if not displayed
//        layout.addComponent(loginForm);

        Label something = new Label("Welcome to the management page");
        something.setStyleName("bold");
        addComponent(something);

        // well, i guess this is now time
        procedureUuids = procedureRunner.getStartedProcedures();
        Table procedureTable = new Table("All procedures:");
        procedureTable.addContainerProperty("Procedure", String.class, null);
        procedureTable.addContainerProperty("State", String.class, null);
        procedureTable.addContainerProperty("User", String.class, null);
        procedureTable.addContainerProperty("Remote object created", Boolean.class, Boolean.FALSE);
        procedureTable.setColumnReorderingAllowed(true);
        procedureTable.setColumnCollapsingAllowed(true);

        for (String uuid : procedureUuids) {
            ProcedureRunnerInterface.STATE state = procedureRunner.queryState(uuid);

            Object itemId = procedureTable.addItem();
            Item row = procedureTable.getItem(itemId);
            row.getItemProperty("Procedure").setValue(uuid);
            row.getItemProperty("State").setValue(state);
            // @TODO procedure table should have user as well
            row.getItemProperty("User").setValue("username");
            if (remotelyCreatedUuids.contains(uuid)) {
                row.getItemProperty("Remote object created").setValue(true);
            } else {
                row.getItemProperty("Remote object created").setValue(false);
            }

        }
        addComponent(procedureTable);


        //addComponent(layout);
    }

    /*private class SimpleLoginForm extends DefaultHorizontalLoginForm {

        public SimpleLoginForm() {
            addLoginListener(new LoginListener() {
                @Override
                public void onLogin(LoginEvent event) {
                    layout.addComponent(new Label(
                            "Logged in with user name " + event.getUserName() +
                                    " and password of length " + event.getPassword().length()
                    ));
                }
            });
        }

        @Override
        protected HorizontalLayout createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {
            HorizontalLayout layout = super.createContent(userNameField, passwordField, loginButton);
            Button clearButton = new Button("Clear");
            clearButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    clear();
                }
            });
            layout.addComponent(clearButton);
            layout.setComponentAlignment(clearButton, Alignment.BOTTOM_LEFT);
            return layout;
        }

    }

    private class NativeLoginForm extends SimpleLoginForm {

        @Override
        protected Button createLoginButton() {
            return new NativeButton("Login");
        }
    }*/

    class ManagementListener implements DistributedObjectListener {

        @Override
        public void distributedObjectCreated(DistributedObjectEvent distributedObjectEvent) {
            String uuid = (String) distributedObjectEvent.getObjectId();
            DistributedObjectEvent.EventType type = distributedObjectEvent.getEventType();
            logger.info("received event " + type + " with uuid: " + uuid);
            remotelyCreatedUuids.add(uuid);
        }

        @Override
        public void distributedObjectDestroyed(DistributedObjectEvent distributedObjectEvent) {
            String uuid = (String) distributedObjectEvent.getObjectId();
            DistributedObjectEvent.EventType type = distributedObjectEvent.getEventType();
            logger.info("received event " + type + " with uuid: " + uuid);
        }
    }
}
