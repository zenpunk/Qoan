package qube.qoan.gui.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import info.bliki.wiki.model.WikiModel;
import qube.qai.network.Network;
import qube.qai.persistence.WikiArticle;

/**
 * Created by rainbird on 11/21/15.
 */
public class WikiContentPanel extends Panel {

    private boolean graphAdded = false;

    private WikiArticle wikiArticle;

    public WikiContentPanel(WikiArticle wikiArticle) {
        this.wikiArticle = wikiArticle;

        initialize(wikiArticle);
    }

    private void initialize(WikiArticle wikiArticle) {
        VerticalLayout layout = new VerticalLayout();

        TabSheet tabbedContent = new TabSheet();
        tabbedContent.setSizeFull();
        String content = WikiModel.toHtml(wikiArticle.getContent());
        Label contentText = new Label(content, ContentMode.HTML);
        Panel contentPanel = new Panel(contentText);
        tabbedContent.addTab(contentPanel).setCaption("Wiki-Article");
        //layout.addComponent(tabbedContent);

        Button addGraphButton = new Button("Add Wiki-Network");
        addGraphButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Network network = Network.createTestNetwork();
                NetworkPanel networkPanel = new NetworkPanel(network);
                networkPanel.setSizeFull();
                tabbedContent.addTab(networkPanel).setCaption("Wiki-Network");
                graphAdded = true;
                addGraphButton.setVisible(!graphAdded);
            }
        });

        // add the components to layout
        layout.addComponent(addGraphButton);
        layout.addComponent(tabbedContent);
        setContent(layout);
    }
}
