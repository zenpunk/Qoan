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

    public WikiArticleTag(String source, WikiArticle wikiArticle) {
        super();

        this.source = source;
        this.wikiArticle = wikiArticle;

        initialize();
    }

    private void initialize() {
        VerticalLayout layout = new VerticalLayout();

        String title = wikiArticle.getTitle();
        Label titleLabel = new Label(title);
        layout.addComponent(titleLabel);

        Label sourceLabel = new Label("from: " + source);
        layout.addComponent(sourceLabel);


        // popup which will expand to display the wiki-content
        String content = WikiModel.toHtml(wikiArticle.getContent());
        Label contentText = new Label(content, ContentMode.HTML);
//        PopupView contentPopup = new PopupView("expand", contentText);
//        layout.addComponent(contentPopup);

        Button open = new Button("open");
        open.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Window window = new Window(title);
                window.setWidth("600px");
                window.setHeight("400px");
                window.setDraggable(true);
                window.setResizable(true);
                window.center();
                window.setContent(contentText);
                // Add it to the root component
                UI.getCurrent().addWindow(window);
            }
        });
        open.setStyleName("link");
        layout.addComponent(open);

        // @TODO get this to work- have a handle on the super-class layout passed in constructor, or something
//        Button remove = new Button("remove");
//        remove.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                Layout parentLayout = (Layout) getParent();
//                parentLayout.removeComponent(super.);
//            }
//        });

        setContent(layout);
    }
}
