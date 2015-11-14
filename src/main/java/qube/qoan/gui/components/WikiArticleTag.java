package qube.qoan.gui.components;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import qube.qai.persistence.WikiArticle;

/**
 * Created by rainbird on 11/13/15.
 */
public class WikiArticleTag extends Panel {

    private WikiArticle wikiArticle;

    public WikiArticleTag(WikiArticle wikiArticle) {
        super();

        this.wikiArticle = wikiArticle;

        initialize();
    }

    private void initialize() {
        VerticalLayout layout = new VerticalLayout();

        Label titleLabel = new Label(wikiArticle.getTitle());
        layout.addComponent(titleLabel);

        // @TODO add an event listener which will expand to display the wiki-content

        setContent(layout);
    }
}
