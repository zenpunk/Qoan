package qube.qoan.gui.components.workspace.finance;

import com.vaadin.ui.*;
import qube.qai.persistence.StockEntity;
import qube.qoan.gui.components.common.InnerPanel;
import qube.qoan.gui.components.workspace.wiki.WikiContentPanel;

/**
 * Created by rainbird on 12/28/15.
 */
public class StockEntityTag extends Panel {

    private StockEntity stockEntity;

    private Layout parentLayout;

    public StockEntityTag(StockEntity stockEntity, Layout parentLayout) {
        super();

        this.stockEntity = stockEntity;
        this.parentLayout = parentLayout;

        initialize();
    }

    private void initialize() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        final String title = stockEntity.getId().toString();
        Label titleLabel = new Label(title);
        titleLabel.setStyleName("bold");
        layout.addComponent(titleLabel);

        Label sourceLabel = new Label("name: " + stockEntity.getSecurity());
        layout.addComponent(sourceLabel);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button open = new Button("open");
        open.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                // @TODO

            }
        });
        open.setStyleName("link");
        buttonsLayout.addComponent(open);

        // add a button to remove this tag from workspace
        Button remove = new Button("remove");
        remove.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // we are removing the toDecorate- which is the dnd-wrapper
                Component parent = getParent();
                parentLayout.removeComponent(parent);
            }
        });
        remove.setStyleName("link");
        buttonsLayout.addComponent(remove);

        layout.addComponent(buttonsLayout);

        setContent(layout);
    }
}
