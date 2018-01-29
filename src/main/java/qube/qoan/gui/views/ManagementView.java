/*
 * Copyright 2017 Qoan Wissenschaft & Software. All rights reserved.
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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import qube.qai.main.QaiConstants;
import qube.qai.procedure.Procedure;
import qube.qai.security.ProcedureManagerInterface;
import qube.qai.user.User;
import qube.qoan.authentication.UserManagerInterface;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

/**
 * Created by rainbird on 11/27/15.
 */
public class ManagementView extends QoanView {

    public static String NAME = "ManagementView";

    @Inject
    private Logger logger;

    @Inject
    private ProcedureManagerInterface procedureManager;

    @Inject
    private UserManagerInterface userManager;

    @Inject
    private HazelcastInstance hazelcastInstance;

    private String nameTemplate = "HazelcastInstance name: '%s'";

    private String versionTemplate = "Hazelcast-Cluster version: '%s'";

    private TabSheet managementTabs;

    private Collection<User> users;

    private ListDataProvider<User> userProvider;

    private Collection<Procedure> procedures;

    private ListDataProvider<Procedure> procedureProvider;

    private boolean isInitialized = false;

    public ManagementView() {
        this.viewTitle = "Qoan Management";
    }

    /**
     * traditional way for getting the gui done,
     * nothing original really
     */
    protected void initialize() {

        if (isInitialized) {
            return;
        }

        HorizontalSplitPanel panel = new HorizontalSplitPanel();

        ClassResource resource = new ClassResource("gui/images/kokoline.gif");
        Image image = new Image("Singularity is nigh!", resource);
        panel.setFirstComponent(image);
        panel.setSplitPosition(15, Unit.PERCENTAGE);

        managementTabs = new TabSheet();
        managementTabs.addTab(createProcedureTab(), "Procedures", new ClassResource("gui/images/proc-icon.png"));
        managementTabs.addTab(createUserTab(), "Users", new ClassResource("gui/images/user-icon.png"));
        managementTabs.addTab(createGridStatsTab(), "Grid-Stats", new ClassResource("gui/images/chart-icon.png"));

        panel.addComponent(managementTabs);
        addComponent(panel);
        isInitialized = true;
    }

    private Component createGridStatsTab() {

        Panel panel = new Panel();
        Layout layout = new VerticalLayout();

        String name = hazelcastInstance.getName();

        Label nameLabel = new Label(String.format(nameTemplate, name));
        layout.addComponent(nameLabel);

//        String clusterVersion = hazelcastInstance.getCluster().getClusterVersion().toString();
//        Label versionLabel = new Label(String.format(versionTemplate, clusterVersion));
//        layout.addComponent(versionLabel);

        Grid<Member> grid = new Grid<>();
        grid.setCaption("Cluster Members");
        grid.addColumn(Member::getUuid).setCaption("UUID:");
        grid.addColumn(Member::getAddress).setCaption("Address:");
        grid.addColumn(Member::getVersion).setCaption("Version:");

        Set<Member> members = hazelcastInstance.getCluster().getMembers();
        ListDataProvider<Member> dataProvider = DataProvider.ofCollection(members);
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();

        layout.addComponent(grid);

        panel.setContent(layout);

        return panel;
    }

    private Component createUserTab() {

        VerticalSplitPanel panel = new VerticalSplitPanel();

        Grid<User> userGrid = new Grid<User>();
        userGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        userGrid.addColumn(User::getUsername).setCaption("Username");
        userGrid.addColumn(User::getEmail).setCaption("E-mail");

        IMap<String, User> userMap = hazelcastInstance.getMap(QaiConstants.USERS);
        users = userMap.values();
        userProvider = DataProvider.ofCollection(users);
        userGrid.setDataProvider(userProvider);

        panel.setFirstComponent(userGrid);

        // create the side pane and add the drop-extension
        Panel sidepane = new Panel();
        AbsoluteLayout layout = new AbsoluteLayout();
        UserDropExtension dropExtension = new UserDropExtension(layout);
        dropExtension.addListener();
        sidepane.setContent(layout);
        panel.setSecondComponent(sidepane);

        return panel;
    }

    private Panel createProcedureTab() {

        Panel panel = new Panel();

        Grid<Procedure> procedureGrid = new Grid<Procedure>();
        procedureGrid.addColumn(Procedure::getProcedureName).setCaption("Name");
        procedureGrid.addColumn(Procedure::getUserName).setCaption("Username");
        procedureGrid.addColumn(Procedure::hasExecuted).setCaption("Has executed");
        procedureGrid.addColumn(Procedure::getDuration).setCaption("Duration");
        procedureGrid.addColumn(Procedure::getProgressPercentage).setCaption("Progress %");
        procedureGrid.addColumn(Procedure::getDescriptionText).setCaption("Description");

        IMap<String, Procedure> procedureMap = hazelcastInstance.getMap(QaiConstants.PROCEDURES);
        procedures = procedureMap.values();
        procedureProvider = DataProvider.ofCollection(procedures);
        procedureGrid.setDataProvider(procedureProvider);

        panel.setContent(procedureGrid);

        return panel;
    }

    /*class ManagementListener implements DistributedObjectListener {

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
    }*/
}
