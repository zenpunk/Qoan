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

import com.google.inject.name.Named;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.*;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.UUIDServiceInterface;
import qube.qoan.QoanUI;
import qube.qoan.gui.components.workspace.search.SearchMenu;
import qube.qoan.services.ProcedureCache;

import javax.inject.Inject;

/**
 * Created by rainbird on 11/2/15.
 */
public class WorkSpace extends Panel {

    private String WIKIPEDIA = "WIKIPEDIA_EN";

    private String WIKTIONARY = "WIKTIONARY_EN";

    private String STOCK_QUOTES = "STOCK_QUOTES";

    private String STOCK_ENTITIES = "STOCK_ENTITIES";

    private HorizontalLayout layout;

    private TabSheet workspaceTabs;

    private SearchMenu searchMenu;

    @Inject
    @Named("Wiktionary_en")
    private SearchServiceInterface wiktionarySearchService;

    @Inject
    @Named("Wikipedia_en")
    private SearchServiceInterface wikipediaSearchService;

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Inject
    private ProcedureCache procedureCache;

    @Inject
    private UUIDServiceInterface uuidService;

    public WorkSpace(SearchMenu searchMenu) {
        super();

        this.searchMenu = searchMenu;

        // do the initialization
        initialize();
    }

    private void initialize() {

        // as always begin with initializing the layout
        layout = new HorizontalLayout();

        workspaceTabs = new TabSheet();

        WorkspacePanel panel = new WorkspacePanel("Workspace 1");
        ((QoanUI) QoanUI.getCurrent()).getInjector().injectMembers(panel);
        workspaceTabs.addTab(panel).setCaption("Workspace 1");

        layout.addComponent(workspaceTabs);

        setContent(layout);
    }

    /**
     * adds a new tab to the view... mainly for the button below
     */
    public void addNewTab() {
        int count = workspaceTabs.getComponentCount() + 1;
        String title = "Workspace " + count;
        WorkspacePanel panel = new WorkspacePanel(title);
        ((QoanUI) QoanUI.getCurrent()).getInjector().injectMembers(panel);
        workspaceTabs.addTab(panel).setCaption(title);
    }

    /**
     * creates a drag-n-drop handler which would handle the added table rows
     *
     * @return
     */
    public DropHandler createDropHandler(AbsoluteLayout parentLayout) {
        DropHandler dropHandler = new MoveAndDropHandler(parentLayout);
        return dropHandler;
    }

    /**
     * @TODO can this class be made to be independent?
     * Handles drops both on an AbsoluteLayout and
     * on components contained within it
     */
    public class MoveAndDropHandler implements DropHandler {

        int left = 40;
        int top = 40;

        AbsoluteLayout parentLayout;

        public MoveAndDropHandler(AbsoluteLayout layout) {
            this.parentLayout = layout;
        }

        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }

        public void drop(DragAndDropEvent event) {

            Transferable transferable = event.getTransferable();

            // check what source we are receiving events from
            if (transferable instanceof DragAndDropWrapper.WrapperTransferable) {
                handleDragEvent(event);
            }
////            else if (transferable instanceof DataBoundTransferable) {
////                handleDropEvent((DataBoundTransferable) transferable);
////            }
//
        }
//
//        /**
//         * this handles the dragging on the layout itself
//         * and changes the positions of the components
//         * which are being dragged around
//         *
//         * @param transferable
//         * @TODO extend this to work selectors
//         */
//        private void handleDropEvent(DataBoundTransferable transferable) {
//
//            Object itemId = transferable.getItemId();
//            Container container = transferable.getSourceContainer();
//            Item item = container.getItem(itemId);
//
//            DragAndDropWrapper tagWrapper = null;
//
//            if (itemId instanceof ProcedureListPanel.ProcedureItem) {
//
//                ProcedureListPanel.ProcedureItem pItem = (ProcedureListPanel.ProcedureItem) itemId;
//                Procedure procedure = procedureCache.getProcedureWithName(pItem.getUuid());
//                ProcedureTag procedureTag = new ProcedureTag(procedure, parentLayout);
//                tagWrapper = new DragAndDropWrapper(procedureTag);
//
//            } else if (item.getItemPropertyIds().contains("File")) {
//
//                String file = (String) item.getItemProperty("File").getValue();
//                String source = (String) item.getItemProperty("Source").getValue();
//
//                WikiArticle wikiArticle = null;
//                IMap<String, WikiArticle> map = null;
//                if ("Wikipedia".equalsIgnoreCase(source)) {
//                    map = hazelcastInstance.getMap(WIKIPEDIA);
//                } else if ("Wiktionary".equalsIgnoreCase(source)) {
//                    map = hazelcastInstance.getMap(WIKTIONARY);
//                }
//
//                wikiArticle = map.get(file);
//                if (wikiArticle == null) {
//                    return;
//                }
//
//                wikiArticle.setSource(source);
//                wikiArticle.setId(uuidService.createUUIDString());
//
//                WikiArticleTag wikiTag = new WikiArticleTag(source, wikiArticle, parentLayout);
//                tagWrapper = new DragAndDropWrapper(wikiTag);
//
//            } else if (item.getItemPropertyIds().contains("Ticker symbol")) {
//
//                IMap<String, StockEntity> map = hazelcastInstance.getMap(STOCK_ENTITIES);
//                String property = (String) item.getItemProperty("Ticker symbol").getValue();
//                String tradedIn = StringUtils.substringBetween(property, "{{", "|");
//                String ticker = StringUtils.substringBetween(property, "|", "}}");
//                String id = ""; //new StockEntityId(ticker, tradedIn);
//                StockEntity stockEntity = map.get(id);
//                StockEntityTag stockTag = new StockEntityTag(stockEntity, parentLayout);
//                tagWrapper = new DragAndDropWrapper(stockTag);
//
//            }
//
//            tagWrapper.setSizeUndefined();
//            tagWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
//
//            // try to find out the component position to enter
//            String positionString;
//            left += 10;
//            top += 10;
//            positionString = "left: " + left + "px; top: " + top + "px;";
//            parentLayout.addComponent(tagWrapper, positionString);
//
//        }

        /**
         * this handles the drop events from external sources
         * in this case, drops from search-menu result-table on workspace
         *
         * @param event
         */
        private void handleDragEvent(DragAndDropEvent event) {
            DragAndDropWrapper.WrapperTransferable t =
                    (DragAndDropWrapper.WrapperTransferable) event.getTransferable();
            DragAndDropWrapper.WrapperTargetDetails details =
                    (DragAndDropWrapper.WrapperTargetDetails) event.getTargetDetails();

            // Calculate the drag coordinate difference
            int xChange = details.getMouseEvent().getClientX()
                    - t.getMouseDownEvent().getClientX();
            int yChange = details.getMouseEvent().getClientY()
                    - t.getMouseDownEvent().getClientY();

            // Move the component in the absolute parentLayout
            AbsoluteLayout.ComponentPosition pos = parentLayout.getPosition(t.getSourceComponent());
            // check to make sure there is indeed a position to be changed
            // can happen that something dragged on layout, but not part of the
            // layout is being dropped- we can't change positions of those, after all
            if (pos != null) {
                pos.setLeftValue(pos.getLeftValue() + xChange);
                pos.setTopValue(pos.getTopValue() + yChange);
            }
        }
    }
//    public void addComponentToDisplay(Component component) {
//        layout.addComponent(component);
//    }
}
