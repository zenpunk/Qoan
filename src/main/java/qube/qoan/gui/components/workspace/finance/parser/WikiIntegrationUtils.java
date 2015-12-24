package qube.qoan.gui.components.workspace.finance.parser;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;
import info.bliki.wiki.filter.HTMLConverter;
import info.bliki.wiki.model.WikiModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        String html = wikiToHtml(wikiArticle);
        return convertHtmlTable(wikiArticle.getTitle(), html);
    }

    public Table convertHtmlTable(String title, String html) {

        String[] header = stripHeader(html);
        String[][] data = stripTableData(html);

        Table table = new Table(title);
        table.setVisible(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setDragMode(Table.TableDragMode.ROW);
        table.setColumnReorderingAllowed(true);
        table.setSizeUndefined();
        table.setColumnCollapsingAllowed(true);
        table.setFooterVisible(true);
        table.setSortAscending(true);
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
     * strips the header out of a table
     * @param html
     * @return
     */
    public String[] stripHeader(String html) {

        //<div style="page-break-inside: avoid;">
        //<table class="wikitable sortable">

        Document doc = Jsoup.parse(html);
        Element table = doc.select("table.wikitable").first();
        if (table == null) {
            logger.error("table not found- is this really a wiki-table?");
            return null;
        }
        Elements header = table.select("th");
        StringBuffer headerBuffer = new StringBuffer();
        String[] titles = new String[header.size()];
        for (int i = 0; i < header.size(); i++) {
            Element element = header.get(i);
            Elements children = element.children();
            if (children != null && children.size() > 0) {
                titles[i] = children.get(0).text();
            } else {
                titles[i] = element.text();
            }
        }

        return titles;
    }

    /**
     * converts contents of wikiModel to html-string
     * @param wikiArticle
     * @return
     */
    public static String wikiToHtml(WikiArticle wikiArticle) {
        return wikiToHtml(wikiArticle.getContent());
    }

    /**
     * converts contents of wikiModel to html-string
     * @param wiki
     * @return
     */
    public static String wikiToHtml(String wiki) {
        StringBuilder builder = new StringBuilder();
        WikiModel model = createModel(wiki, builder);
        return builder.toString();
    }

    /**
     * creates a model out of the given html
     * @param wiki
     * @param builder
     * @return
     */
    public static WikiModel createModel(String wiki, StringBuilder builder) {
        WikiModel wikiModel = new WikiModel("${image}", "${title}");
        try {
            WikiModel.toText(wikiModel, new HTMLConverter(), wiki, builder, false, false);
        } catch (IOException e) {
            logger.error("error while converting wiki-data to html");
        }

        return wikiModel;
    }

    /**
     * creates a model out of the given html
     * @param article
     * @param builder
     * @return
     */
    public static WikiModel createModel(WikiArticle article, StringBuilder builder) {
        return createModel(article.getContent(), builder);
    }

    /**
     * creates a model of the wiki-article
     * and returns the links found in that model
     * @param article
     * @return
     */
    public static Collection<String> getLinksOf(WikiArticle article) {
        StringBuilder builder = new StringBuilder();
        return createModel(article, builder).getLinks();
    }

    /**
     * strips the data found in a given wiki-html table
     * assumes that the html is from wiki nad as the style "wikitable"
     * @param html
     * @return
     */
    public String[][] stripTableData(String html) {
        Document doc = Jsoup.parse(html);
        Element table = doc.select("table.wikitable").first();
        Elements rows = table.select("tr");

        // in order to initialize
        int rowCount = rows.size();
        int columnCount = 0;
        for (int i = 0; i < rowCount; i++) {
            Element row = rows.get(i);
            Elements columns = row.select("td");
            columnCount = columns.size();
            break;
        }

        String[][] data = new String[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            for (int j = 0; j < columnCount; j++) {
                Element td = cols.get(j);
                data[i][j] = td.text();
            }
        }

        return data;
    }

}
