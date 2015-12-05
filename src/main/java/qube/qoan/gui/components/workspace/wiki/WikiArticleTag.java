package qube.qoan.gui.components.workspace.wiki;

import com.vaadin.ui.*;
import qube.qai.persistence.WikiArticle;
import qube.qoan.gui.components.common.InnerPanel;

/**
 * Created by rainbird on 11/13/15.
 */
public class WikiArticleTag extends Panel {

    private String source;

    private int top = 100;

    private int left = 100;

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
        titleLabel.setStyleName("bold");
        layout.addComponent(titleLabel);

        Label sourceLabel = new Label("from: " + source);
        layout.addComponent(sourceLabel);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button open = new Button("open");
        open.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                WikiContentPanel contentPanel = new WikiContentPanel(wikiArticle);
                contentPanel.setSizeFull();

                //Window window = new Window(title);
                InnerPanel window = new InnerPanel(title, contentPanel);
                window.setWidth("600px");
                window.setHeight("400px");
                // if parent is an absolute layout, we need a position to add the thing as well
                if (parentLayout instanceof AbsoluteLayout) {
                    left = left + 5;
                    top = top + 5;
                    String positionString = "left: " + left + "px; top: " + top + "px;";
                    DragAndDropWrapper dndWrapper = new DragAndDropWrapper(window);
                    dndWrapper.setSizeUndefined();
                    dndWrapper.setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
                    ((AbsoluteLayout)parentLayout).addComponent(dndWrapper, positionString);
                } else {
                    parentLayout.addComponent(window);
                }

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
