package qube.qoan.gui.components.workspace.finance.parser;

import com.vaadin.ui.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.SearchServiceInterface;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rainbird on 12/23/15.
 */
public class TestWikiArticleIntegration extends QoanTestBase {

    private static Logger logger = LoggerFactory.getLogger("TestWikiArticleIntegration");

    private String stockListingPage = "Lists of companies by stock exchange listing.xml";

    private String SnP500Page = "List of S&P 500 companies.xml";

    @Inject
    @Named("Wikipedia_en")
    private SearchServiceInterface searchService;

    /**
     * i have to find a way for making the wiki-pages
     * useful... we already know, company-name finder
     * functions pretty well.
     * @throws Exception
     */
    /*public void restSnP500Fun() throws Exception {

        WikiArticle snp500 = searchService.retrieveDocumentContentFromZipFile(SnP500Page);
        assertNotNull("we are here to play with this file", snp500);

        // we first have to tokenize
        InputStream enTokenStream = getClass().getResourceAsStream("/opennlp/en-token.bin");
        TokenizerModel tokenizerModel = new TokenizerModel(enTokenStream);
        Tokenizer tokenizer = new TokenizerME(tokenizerModel);
        enTokenStream.close();

        // this is the organization-name finder
        InputStream enOrganizationStream = getClass().getResourceAsStream("/opennlp/en-ner-organization.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(enOrganizationStream);
        enOrganizationStream.close();

        String[] tokens = tokenizer.tokenize(snp500.getContent());
        NameFinderME nameFinder = new NameFinderME(model);
        Span[] names = nameFinder.find(tokens);
        for (Span span : names) {
            //logger.info("found name-span: '" + span.toString() + "'");
            int start = span.getStart();
            int end = span.getEnd();
            StringBuffer buffer = new StringBuffer();
            for (int i = start; i < end; i++) {
                buffer.append(tokens[i]);
                buffer.append(" ");
            }
            buffer.deleteCharAt(buffer.length() - 1);
            log("found name: '" + buffer.toString() + "'");
        }
    }*/

    /**
     * open-nlp is fantastic in finding names of company names,
     * but having the names alone won't help in this case
     * so we try our hand with jSoup
     */
    public void restTableParsing() throws Exception {

        WikiArticle snp500 = searchService.retrieveDocumentContentFromZipFile(SnP500Page);
        assertNotNull("we are here to play with this file", snp500);

        String html = WikiIntegrationUtils.wikiToHtml(snp500);
        log(html);

        // here goes nothing...
//        StringBuilder bufferOut = new StringBuilder();
//        Document doc = Jsoup.parse(bufferOut.toString());
//        Element table = doc.select("table").get(1); //select the first table.
//        Elements header = table.select("th");
//        StringBuffer headerBuffer = new StringBuffer();
//        for (int i = 0; i < header.size(); i++) {
//            Element element = header.get(i);
//            Elements children = element.children();
//            if (children != null && children.size() > 0) {
//                headerBuffer.append(children.get(0).text());
//            } else {
//                headerBuffer.append(element.text());
//            }
//            headerBuffer.append(" ");
//        }
//        log("header: " + headerBuffer.toString());
//
//        Elements rows = table.select("tr");
//        for (int i = 1; i < rows.size(); i++) {
//            Element row = rows.get(i);
//            Elements cols = row.select("td");
//            StringBuffer buffer = new StringBuffer();
//            for (int j = 0; j < cols.size(); j++) {
//                Element td = cols.get(j);
//                buffer.append(td.text());
//                buffer.append(" ");
//            }
//            log("row: " + buffer.toString());
//        }

    }

    public void testVaadinTable() throws Exception {
        WikiArticle snp500 = searchService.retrieveDocumentContentFromZipFile(SnP500Page);
        assertNotNull("we are here to play with this file", snp500);

        // and now... tataa
        WikiIntegrationUtils wiki = new WikiIntegrationUtils();
        Table table = wiki.convertHtmlTable(snp500);
        assertNotNull("this should really not happen", table);
        String[] columnHeaders = table.getColumnHeaders();
        assertNotNull("has the table not been initialized?", columnHeaders);
        Set<String> headerTitles = headerTitles();
        assertTrue(columnHeaders.length == headerTitles.size());
        for (String header : columnHeaders) {
            assertTrue("these should be same", headerTitles.contains(header));
        }

        // i don't know how i could check the data though
    }

    /**
     * these are the header titles on S&P 500 page table
     * @return
     */
    private Set<String> headerTitles() {
        String[] headerTitles = {"Ticker symbol", "Security",
                "SEC filings", "GICS", "GICS Sub Industry",
                "Address of Headquarters", "Date first added", "CIK"};

        Set<String> titles = new HashSet<>();
        for (String title : headerTitles) {
            titles.add(title);
        }

        return titles;
    }

    /**
     * this time it is even right to have this
     * logger-lines are terrible for reading
     * and getting this test done requires a lot of reading
     * @param message
     */
    private void log(String message) {
        System.out.println(message);
    }
}
