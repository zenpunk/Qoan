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

package qube.qoan.gui.components.common.tags;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import qube.qai.user.User;
import qube.qoan.gui.components.common.decorators.WikiDecorator;
import qube.qoan.gui.components.management.UserEditPanel;
import qube.qoan.gui.views.UserData;
import qube.qoan.services.QoanInjectorService;

public class UserTag extends BaseTag {

    protected User user;

    public UserTag(Layout parentLayout, User user) {
        super(parentLayout, user);
        iconImage = new Image(searchResult.getContext(),
                new ClassResource("gui/images/user-icon.png"));
        decorators.put("Wiki-article", new WikiDecorator());
        this.user = user;
    }

    @Override
    public void onOpenClicked() {

        UserEditPanel content = new UserEditPanel(new UserData(user));
        QoanInjectorService.getInstance().getInjector().injectMembers(content);

        addToParent(content);
    }
}
