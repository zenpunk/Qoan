package qube.qoan.gui.components.workspace.finance.parser;

import com.vaadin.ui.Table;
import info.bliki.wiki.model.WikiModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import qube.qai.persistence.WikiArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by rainbird on 12/21/15.
 */
public class WikiIntegration {

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
        return convertHtmlTable(wikiArticle.getContent());
    }

    public Table convertHtmlTable(String html) {
        Table table = new Table();

        return table;
    }

    /**
     * strips the header out of a table
     * @param html
     * @return
     */
    public String[] stripHeader(String html) {
        StringBuilder bufferOut = new StringBuilder();
        Document doc = Jsoup.parse(bufferOut.toString());
        Element table = doc.select("table").get(1); //select the first table.
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

    public String[][] stripTable(String html) {
        return null;
    }

}
