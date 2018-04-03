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

package qube.qoan.gui.components.management;

import qube.qoan.services.QoanInjectorService;
import qube.qoan.services.QoanTestBase;

/**
 * Created by rainbird on 7/2/17.
 */
public class ManagementPanelTest extends QoanTestBase {

    public void testManagementPanel() throws Exception {

        ManagementPanel managementPanel = new ManagementPanel();
        QoanInjectorService.getInstance().injectMembers(managementPanel);

        fail("implement the test first");
    }
}
