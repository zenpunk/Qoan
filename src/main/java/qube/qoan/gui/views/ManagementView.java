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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.procedure.ProcedureManagerInterface;
import qube.qoan.authentication.UserManagerInterface;

import javax.inject.Inject;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by rainbird on 11/27/15.
 */
public class ManagementView extends QoanView {

    private static Logger logger = LoggerFactory.getLogger("ManagementView");

    public static String NAME = "ManagementView";

    @Inject
    private ProcedureManagerInterface procedureManager;

    @Inject
    private UserManagerInterface userManager;

    protected Set<String> procedureUuids;

    protected Set<String> remotelyCreatedUuids;

    public ManagementView() {
        this.viewTitle = "Qoan Management";
    }

    /**
     * traditional way for getting the gui done,
     * nothing original really
     */
    protected void initialize() {

        remotelyCreatedUuids = new TreeSet<String>();

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
