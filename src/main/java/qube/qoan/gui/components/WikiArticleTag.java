package qube.qoan.gui.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import info.bliki.wiki.model.WikiModel;
import qube.qai.persistence.WikiArticle;

/**
 * Created by rainbird on 11/13/15.
 */
public class WikiArticleTag extends Panel {

    private String source;

    private WikiArticle wikiArticle;

    private Layout parentLayout;

    public WikiArticleTag(String source, WikiArticle wikiArticle, Layout parentLayout) {
        super();

        this.source = source;
        this.wikiArticle = wikiArticle;
        this.parentLayout = parentLayout;

        initialize();
    }

    private void initialize() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        String title = wikiArticle.getTitle();
        Label titleLabel = new Label(title);
        layout.addComponent(titleLabel);

        Label sourceLabel = new Label("from: " + source);
        layout.addComponent(sourceLabel);


        // popup which will expand to display the wiki-content
//        String content = WikiModel.toHtml(wikiArticle.getContent());
//        Label contentText = new Label(content, ContentMode.HTML);
//        PopupView contentPopup = new PopupView("expand", contentText);
//        layout.addComponent(contentPopup);

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button open = new Button("open");
        open.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                WikiContentPanel contentPanel = new WikiContentPanel(wikiArticle);
                contentPanel.setSizeFull();

                Window window = new Window(title);
                window.setWidth("600px");
                window.setHeight("400px");
                window.setDraggable(true);
                window.setResizable(true);
                window.center();
                window.setContent(contentPanel);
                // Add it to the root component
                UI.getCurrent().addWindow(window);
            }
        });
        open.setStyleName("link");
        buttonsLayout.addComponent(open);

        // add a button to remove this tag from workspace
        Button remove = new Button("remove");
        remove.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // we are removing the parent- which is the dnd-wrapper
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
