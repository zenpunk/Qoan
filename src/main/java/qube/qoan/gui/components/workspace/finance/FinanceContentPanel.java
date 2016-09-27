package qube.qoan.gui.components.workspace.finance;

import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import qube.qai.persistence.StockEntity;

/**
 * Created by rainbird on 6/6/16.
 */
public class FinanceContentPanel extends Panel {

    private StockEntity stockEntity;

    public FinanceContentPanel(StockEntity stockEntity) {
        this.stockEntity = stockEntity;

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();

        final TabSheet tabbedContent = new TabSheet();

    }
}
