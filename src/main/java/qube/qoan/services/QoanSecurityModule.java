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

package qube.qoan.services;

import com.google.inject.Provides;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.ShiroModule;
import org.apache.shiro.realm.text.IniRealm;
import qube.qoan.authentication.QoanRealm;

/**
 * Created by rainbird on 7/19/17.
 */
public class QoanSecurityModule extends ShiroModule {


    public QoanSecurityModule() {
        super();
    }

    @Override
    protected void configureShiro() {
        try {
            bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
        } catch (NoSuchMethodException e) {
            addError(e);
        }

        this.bindRealm().to(QoanRealm.class);
    }

    @Provides
    Ini loadShiroIni() {
        return Ini.fromResourcePath("classpath:qube/qoan/services/shiro.ini");
    }

}
