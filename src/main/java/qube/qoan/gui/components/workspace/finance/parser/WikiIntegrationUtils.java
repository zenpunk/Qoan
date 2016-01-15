package qube.qoan.gui.components.workspace.finance.parser;

import com.vaadin.data.Item;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import info.bliki.wiki.filter.HTMLConverter;
import info.bliki.wiki.model.WikiModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.parsers.WikiIntegration;
import qube.qai.persistence.WikiArticle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by rainbird on 12/21/15.
 */
public class WikiIntegrationUtils {

    private static Logger logger = LoggerFactory.getLogger("WikiIntegrationUtils");

    /**
     * this at least at this point, a class to integrate
     * text from wiki-articles in vaadin surrounding.
     * for starters, we will be converting wiki-tables
     * to vaadin tables, so that they can be used in
     * the gui and allow better user-interaction
     */
    public WikiIntegrationUtils() {
    }

    public Table convertHtmlTable(WikiArticle wikiArticle) {
        String html = WikiIntegration.wikiToHtml(wikiArticle);
        return convertHtmlTable(wikiArticle.getTitle(), html);
    }

    public Table convertHtmlTable(String title, String html) {

        String[] header = WikiIntegration.stripHeader(html);
        String[][] data = WikiIntegration.stripTableData(html);

        Table table = new Table();
        table.setVisible(true);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setDragMode(Table.TableDragMode.ROW);
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setFooterVisible(true);
        table.setSortAscending(true);
        table.setPageLength(9);
        //table.setSizeUndefined();
        // begin with adding the headers
        for (int i = 0; i < header.length; i++) {
            table.addContainerProperty(header[i], String.class, null);
        }

        // now add the data
        for (int i = 0; i < data.length; i++) {
            Object itemId = table.addItem();
            Item row = table.getItem(itemId);
            for (int j = 0; j < data[i].length; j++) {
                row.getItemProperty(header[j]).setValue(data[i][j]);
            }
        }

        return table;
    }

    /**
     * method adds contents of the wiki-article to the layout
     * images and tables will be replaced by vaadin images and tables
     * @param article
     * @param layout
     */
    public void addWikiContentsToLayout(WikiArticle article, Layout layout) {

        String html = WikiIntegration.wikiToHtml(article);

        logger.info(html);
        // @TODO
    }
}
