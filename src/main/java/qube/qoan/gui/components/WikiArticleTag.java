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

        Label titleLabel = new Label(wikiArticle.getTitle());
        layout.addComponent(titleLabel);

        Label sourceLabel = new Label("from: " + source);
        layout.addComponent(sourceLabel);


        // @TODO add an event listener which will expand to display the wiki-content
        String content = WikiModel.toHtml(wikiArticle.getContent());
        Label contentText = new Label(content, ContentMode.HTML);
        PopupView contentPopup = new PopupView("expand", contentText);
        layout.addComponent(contentPopup);


        setContent(layout);
    }
}
