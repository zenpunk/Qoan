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

package qube.qoan.authentication;

import qube.qoan.services.QoanSecurityModule;
import qube.qoan.services.QoanTestBase;

public class TestQoanSecurity extends QoanTestBase {

    public void testQoanSecurity() throws Exception {

        QoanSecurityModule securityModule = new QoanSecurityModule(null);
        assertNotNull("nona", securityModule);

        fail("the test not yet implemented");
    }
}
