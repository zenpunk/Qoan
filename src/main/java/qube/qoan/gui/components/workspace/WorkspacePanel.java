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

package qube.qoan.gui.components.workspace;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.persistence.DataProvider;
import qube.qai.persistence.WikiArticle;

import javax.inject.Inject;

/**
 * Created by rainbird on 11/14/15.
 */
public class WorkspacePanel extends Panel {

    private static Logger logger = LoggerFactory.getLogger("WorkspacePanel");

    @Inject
    private DataProvider<WikiArticle> wikiProvider;

    private String title;

    public WorkspacePanel(String title) {
        this.title = title;

        initialize();
    }

    private void initialize() {

        AbsoluteLayout layout = new AbsoluteLayout();

        Label titleLabel = new Label(title);
        layout.addComponent(titleLabel, "right: 50px; top: 50px;");
        layout.setWidth("1000px");
        layout.setHeight("800px");
        setContent(layout);

        // now we deal with drop events and all...
        /*DropTargetExtension<AbsoluteLayout> dropExtension = new DropTargetExtension<>(layout);
        dropExtension.setDropEffect(DropEffect.MOVE);
        dropExtension.addDropListener(event -> {
            Optional<AbstractComponent> dragSource = event.getDragSourceComponent();
            if (dragSource.isPresent() && dragSource.get() instanceof Grid) {
                String jsonSet = event.getDataTransferText();
                if (StringUtils.isNotBlank(jsonSet)) {
                    XStream xStream = new XStream();
                    Object resultObject = null;
                    try {
                        resultObject = xStream.fromXML(jsonSet);
                    } catch (Exception e) {
                        logger.info("trouble converting the data-string: " + e.getMessage());
                        return;
                    }
                    int offset = 0;
                    if (resultObject instanceof Collection) {
                        Collection<SearchResult> results = (Collection<SearchResult>) resultObject;
                        for (SearchResult result : results) {
                            //wikiProvider.setContext(result.getContext());
                            WikiArticle article = null; // wikiProvider.getData(result.getUuid());
                            if (article == null) {
                                String message = "WikiArticle context:'" + result.getContext() + "' uuid: '" + result.getUuid() + "' could not be found!";
                                //throw new RuntimeException();
                                Label display = new Label("Context: " + result.getContext() + " uuid: " + result.getContext());
                                int clientX = event.getMouseEventDetails().getClientX();
                                int clientY = event.getMouseEventDetails().getClientY();
                                String coords = String.format("leftt: %d px; top: %d px;", clientY + offset, clientX + offset);
                                //layout.addComponent(tag, coords);
                                offset += 5;
                                layout.addComponent(display, coords);
                            } else {
                                // create and add the thing on screen somewhere.
                                WikiArticleTag tag = new WikiArticleTag(result.getContext(), article, layout);
                                //Label display = new Label("Context: " + result.getContext() + " uuid: " + result.getContext());
                                int clientX = event.getMouseEventDetails().getClientX();
                                int clientY = event.getMouseEventDetails().getClientY();
                                String coords = String.format("leftt: %d px; top: %d px;", clientY + offset, clientX + offset);
                                //layout.addComponent(tag, coords);
                                offset += 5;
                                layout.addComponent(tag, coords);
                            }

                        }
                    }
                }
            }
        });*/
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
