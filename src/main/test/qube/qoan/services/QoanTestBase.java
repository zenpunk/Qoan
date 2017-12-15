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

package qube.qoan.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.main.QaiConstants;

/**
 * Created by rainbird on 11/19/15.
 */
public class QoanTestBase extends TestCase implements QaiConstants {

    protected Logger logger = LoggerFactory.getLogger("QoanTestBase");

    protected Injector injector;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        logger.info("injecting members for the test");

        if (injector == null) {
            injector = Guice.createInjector(new QoanTestModule(), new QoanTestSecurityModule());
            injector.injectMembers(this);
        }
    }

}
