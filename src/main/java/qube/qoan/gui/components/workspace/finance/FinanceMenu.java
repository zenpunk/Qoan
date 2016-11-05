package qube.qoan.gui.components.workspace.finance;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by rainbird on 11/18/15.
 */
public class FinanceMenu extends Panel {

    private FinanceListing financeListing;
    private FinanceRepository financeRepository;

    public FinanceMenu() {

        initialize();
    }

    private void initialize() {

        VerticalLayout layout = new VerticalLayout();
        //layout.setWidth("300px");

        financeRepository = new FinanceRepository();
        layout.addComponent(financeRepository);

        Button showSelectedListingButton = new Button("Show selected listing");
        showSelectedListingButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                String listingName = financeRepository.getSelectedListingName();
                financeListing.displayListing(listingName);
            }
        });
        showSelectedListingButton.setStyleName("link");
        layout.addComponent(showSelectedListingButton);

        financeListing = new FinanceListing();
        layout.addComponent(financeListing);

        setContent(layout);
    }
}
