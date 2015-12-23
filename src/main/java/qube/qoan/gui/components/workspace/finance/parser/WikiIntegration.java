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
import java.util.List;
import java.util.Set;

/**
 * Created by rainbird on 12/21/15.
 */
public class WikiIntegration {

    private static Logger logger = LoggerFactory.getLogger("WikiIntegration");

    /**
     * this at least at this point, a class to integrate
     * text from wiki-articles in vaadin surrounding.
     * for starters, we will be converting wiki-tables
     * to vaadin tables, so that they can be used in
     * the gui and allow better user-interaction
     */
    public WikiIntegration() {
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

    public static String wikiToHtml(WikiArticle wikiArticle) {
        return wikiToHtml(wikiArticle.getContent());
    }

    public static String wikiToHtml(String wiki) {
        StringBuilder builder = new StringBuilder();
        WikiModel model = createModel(wiki, builder);
        return builder.toString();
    }

    public static WikiModel createModel(String html, StringBuilder builder) {
        WikiModel wikiModel = new WikiModel("${image}", "${title}");
        try {
            WikiModel.toText(wikiModel, new HTMLConverter(), html, builder, false, false);
        } catch (IOException e) {
            logger.error("error while converting wiki-data to html");
        }

        return wikiModel;
    }

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
