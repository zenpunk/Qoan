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
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.decorators.WikiDecorator;

/**
 * Created by rainbird on 11/13/15.
 */
public class WikiArticleTag extends BaseTag {

    public WikiArticleTag(Layout parentLayout, SearchResult searchResult) {
        super(parentLayout, searchResult);
        iconImage = new Image(searchResult.getContext(),
                new ClassResource("gui/images/wiki.png"));
        decorators.put("Wiki-article", new WikiDecorator());
        //decorators.put("Semantic network", new NetworkDecorator());
    }

}
