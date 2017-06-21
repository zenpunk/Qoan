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

package qube.qoan.gui.components.common;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * Created by rainbird on 6/21/17.
 * This is supposed to add ngl-viewer in the application as a
 * Vaadin addon. should this work well enough, later i should consider
 * to make it in its own library, so that it can be released as a real Vaadin
 * addon too. for that is for later...
 */
@JavaScript({"ngl/ngl_viewer_adapter.js"})
public class NglAdapter extends AbstractJavaScriptComponent {

    public NglAdapter(String xhtml) {
        getState().xhtml = xhtml;
    }

    /**
     *
     */
    @Override
    protected NglAdapterState getState() {
        return (NglAdapterState) super.getState();
    }
}
