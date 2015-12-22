package qube.qoan.gui.views;

import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.services.ProcedureRunnerInterface;
import qube.qoan.gui.components.common.QoanHeader;

import javax.inject.Inject;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by rainbird on 11/27/15.
 */
public class ManagementView extends VerticalLayout implements View {

    private static Logger logger = LoggerFactory.getLogger("ManagementView");

    public static String NAME = "ManagementView";

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Inject
    private ProcedureRunnerInterface procedureRunner;

    protected Set<String> procedureUuids;

    protected Set<String> remotelyCreatedUuids;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        remotelyCreatedUuids = new TreeSet<String>();

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");

        QoanHeader header = new QoanHeader();
        layout.addComponent(header);

        Label something = new Label("Welcome to the management page");
        something.setStyleName("bold");
        layout.addComponent(something);

        // well, i guess this is now time
        procedureUuids = procedureRunner.getStartedProcedures();
        Table procedureTable = new Table("All procedures:");
        procedureTable.addContainerProperty("Procedure", String.class, null);
        procedureTable.addContainerProperty("State", String.class, null);
        procedureTable.addContainerProperty("Remote object created", Boolean.class, Boolean.FALSE);
        procedureTable.setColumnReorderingAllowed(true);
        procedureTable.setColumnCollapsingAllowed(true);

        for (String uuid : procedureUuids) {
            ProcedureRunnerInterface.STATE state = procedureRunner.queryState(uuid);

            Object itemId = procedureTable.addItem();
            Item row = procedureTable.getItem(itemId);
            row.getItemProperty("Procedure").setValue(uuid);
            row.getItemProperty("State").setValue(state);
            if (remotelyCreatedUuids.contains(uuid)) {
                row.getItemProperty("Remote object created").setValue(true);
            } else {
                row.getItemProperty("Remote object created").setValue(false);
            }

        }
        layout.addComponent(procedureTable);


        addComponent(layout);
    }

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
