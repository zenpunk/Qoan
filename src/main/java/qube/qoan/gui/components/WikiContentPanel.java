package qube.qoan.gui.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import info.bliki.wiki.model.WikiModel;
import qube.qai.network.Network;
import qube.qai.network.semantic.SemanticNetwork;
import qube.qai.persistence.WikiArticle;

/**
 * Created by rainbird on 11/21/15.
 */
public class WikiContentPanel extends Panel {

    private boolean wikiGraphAdded = false;
    private boolean semanticGraphAdded = false;

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

        HorizontalLayout buttonLayout = new HorizontalLayout();

        Button addWikiNetworkButton = new Button("Create Wiki-Network");
        addWikiNetworkButton.setStyleName("link");
        addWikiNetworkButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Network network = Network.createTestNetwork();
                NetworkPanel networkPanel = new NetworkPanel(network);
                networkPanel.setSizeFull();
                tabbedContent.addTab(networkPanel).setCaption("Wiki-Network");
                wikiGraphAdded = true;
                addWikiNetworkButton.setVisible(!wikiGraphAdded);
            }
        });
        buttonLayout.addComponent(addWikiNetworkButton);

        Button addSemanticNetwork = new Button("Create Semantic-Network");
        addSemanticNetwork.setStyleName("link");
        addSemanticNetwork.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                SemanticNetwork semanticNetwork = new SemanticNetwork();
                semanticNetwork.buildNetwork(wikiArticle);
                NetworkPanel networkPanel = new NetworkPanel(semanticNetwork);
                networkPanel.setSizeFull();
                tabbedContent.addTab(networkPanel).setCaption("Semantic-Network");
                semanticGraphAdded = true;
                addSemanticNetwork.setVisible(!semanticGraphAdded);
            }
        });
        buttonLayout.addComponent(addSemanticNetwork);


        // add the components to layout
        layout.addComponent(buttonLayout);
        layout.addComponent(tabbedContent);
        setContent(layout);
    }
}
