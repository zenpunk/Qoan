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

package qube.qoan.gui.components.common.decorators;

import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.services.implementation.SearchResult;

/**
 * Created by rainbird on 7/4/17.
 */
public abstract class BaseDecorator extends Panel implements Decorator {

    protected static Logger logger = LoggerFactory.getLogger("BaseDecorator");

    public BaseDecorator() {
    }

    @Override
    public abstract void decorate(SearchResult toDecorate);

    public abstract Image getIconImage();

}
