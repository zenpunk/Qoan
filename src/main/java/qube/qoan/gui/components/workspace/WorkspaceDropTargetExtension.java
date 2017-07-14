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

import com.thoughtworks.xstream.XStream;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.dnd.DropTargetExtension;
import org.apache.commons.lang.StringUtils;
import qube.qai.main.QaiConstants;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.gui.components.common.tags.*;

import java.util.Optional;

/**
 * Created by rainbird on 7/6/17.
 */
public class WorkspaceDropTargetExtension extends DropTargetExtension<AbsoluteLayout>
        implements QaiConstants {

    private AbsoluteLayout targetLayout;

    public WorkspaceDropTargetExtension(AbsoluteLayout target) {
        super(target);
        targetLayout = target;
    }

    public void addListener() {
        addDropListener(event -> {
            // if the drag source is in the same UI as the target
            Optional<AbstractComponent> dragSource = event.getDragSourceComponent();
            if (dragSource.isPresent() && dragSource.get() instanceof Grid) {

                String dropCoords = "left: %d px; top: &d px;";
                int dropX = event.getMouseEventDetails().getClientX();
                int dropY = event.getMouseEventDetails().getClientY();

                // move the label to the layout
                targetLayout.addComponent(dragSource.get());

                // get possible transfer data
                String message = ""; //event.getDataTransferData("text/html");
                if (message != null) {
                    Notification.show("DropEvent with data transfer html: " + message);
                } else {
                    // get transfer text
                    message = event.getDataTransferText();
                    Notification.show("DropEvent with data transfer text: " + message);
                }
                SearchResult result = null;
                if (StringUtils.isNotEmpty(message)) {
                    XStream xStream = new XStream();
                    try {
                        result = (SearchResult) xStream.fromXML(message);
                    } catch (Exception e) {
                        String error = "Exception while de-serializing: '" + message + "' exception: " + e.getMessage();
                        Notification.show(error);
                        return;
                    }
                }

                QoanTag tag;
                if (WIKIPEDIA.equals(result.getContext())) {
                    tag = new WikiArticleTag(result);
                } else if (WIKTIONARY.equals(result.getContext())) {
                    tag = new WikiArticleTag(result);
                } else if (STOCK_ENTITIES.equals(result.getContext())) {
                    tag = new StockEntityTag(result);
                } else if (PROCEDURES.equals(result.getContext())) {
                    tag = new ProcedureTag(result);
                } else if (WIKIPEDIA_RESOURCES.equals(result.getContext())) {
                    tag = new ResourceTag(result);
                } else {
                    tag = new BaseTag(result);
                }

                dropCoords = String.format(dropCoords, dropX, dropY);
                targetLayout.addComponent(tag, dropCoords);

                // handle possible server side drag data, if the drag source was in the same UI
                //event.getDragData().ifPresent(data -> handleMyDragData((MyObject) data));
            }
        });

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
}
