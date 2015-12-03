package qube.qoan.gui.components;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import qube.qoan.gui.components.workspace.search.SearchMenu;
import qube.qoan.services.QoanTestBase;

import java.util.Collection;

/**
 * Created by rainbird on 11/19/15.
 */
public class TestSearchMenu extends QoanTestBase {


    public void testSearchMenuDropAction() throws Exception {

        // @TODO add the proper test here
        SearchMenu searchMenu = new SearchMenu();

        AbsoluteLayout layout = new AbsoluteLayout();
        DropHandler dropHandler = searchMenu.createDropHandler(layout);
        Transferable transferable = new Transferable() {
            @Override
            public Object getData(String dataFlavor) {
                return null;
            }

            @Override
            public void setData(String dataFlavor, Object value) {

            }

            @Override
            public Collection<String> getDataFlavors() {
                return null;
            }

            @Override
            public Component getSourceComponent() {
                return null;
            }
        };
        DragAndDropEvent event = new DragAndDropEvent(transferable, null);
        dropHandler.drop(event);
        fail("implementation details missing!!!");
    }
}
