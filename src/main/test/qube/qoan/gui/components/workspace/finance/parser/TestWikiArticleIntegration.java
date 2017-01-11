package qube.qoan.gui.components.workspace.finance.parser;

import com.hazelcast.core.HazelcastInstance;
import com.vaadin.data.Item;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.parsers.WikiIntegration;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.SearchServiceInterface;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by rainbird on 12/23/15.
 */
public class TestWikiArticleIntegration extends QoanTestBase {

    private static Logger logger = LoggerFactory.getLogger("TestWikiArticleIntegration");

    private String stockListingPage = "Lists of companies by stock exchange listing.xml";

    private String SnP500Page = "List of S&P 500 companies.xml";

    private String darwinArticleName = "Charles Darwin.xml";

    @Inject
    @Named("Wikipedia_en")
    private SearchServiceInterface searchService;

    //@Inject
    //private HazelcastInstance hazelcastInstance;

    public void testVaadinTable() throws Exception {
        WikiArticle snp500 = searchService.retrieveDocumentContentFromZipFile(SnP500Page);
        assertNotNull("we are here to play with this file", snp500);

        // and now... tataa
        WikiIntegrationUtils wikiIntegration = new WikiIntegrationUtils();
        Table table = wikiIntegration.convertHtmlTable(snp500);
        assertNotNull("this should really not happen", table);
        String[] columnHeaders = table.getColumnHeaders();
        assertNotNull("has the table not been initialized?", columnHeaders);
        Set<String> headerTitles = headerTitles();
        assertTrue(columnHeaders.length == headerTitles.size());
        for (String header : columnHeaders) {
            assertTrue("these should be same", headerTitles.contains(header));
        }

        Collection itemIds = table.getItemIds();
        assertNotNull("there have to be some items in there", itemIds);
        assertTrue("and there has to be something in there as well", !itemIds.isEmpty());
        for (Object itemId : itemIds) {
            Item item = table.getItem(itemId);
            logger.info("found item: " + item.toString());
        }

    }

    // @TODO inplement the rest of the test
    public void restInsertEntitiesToMap() throws Exception {

        WikiArticle snp500 = searchService.retrieveDocumentContentFromZipFile(SnP500Page);
        assertNotNull("we are here to play with this file", snp500);

        fail("rest of the test not yet implemented");

    }

    public void restWikiUtilLayout() throws Exception {
        WikiArticle darwin = searchService.retrieveDocumentContentFromZipFile(darwinArticleName);
        assertNotNull("we need this one for the test after all", darwin);

        Layout layout = new VerticalLayout();
        WikiIntegrationUtils wikiIntegration = new WikiIntegrationUtils();
        wikiIntegration.addWikiContentsToLayout(darwin, layout);

        // there are a few images in there and those are what we will be looking for
        int imageCount = 0;
        for (Iterator<Component> it = layout.iterator(); it.hasNext(); ) {
            Component current = it.next();
            if (current instanceof Image) {
                imageCount++;
            }
        }

        logger.info("images replaced: " + imageCount);
        // in the online version of the article there are 19 images
        assertTrue("there has to be some images in there", imageCount > 0);
    }

    /**
     * these are the header titles on S&P 500 page table
     * @return
     */
    private Set<String> headerTitles() {
        String[] headerTitles = {"Ticker symbol", "Security",
                "SEC filings", "GICS", "GICS Sub Industry",
                "Address of Headquarters", "Date first added", "CIK"};

        Set<String> titles = new HashSet<String>();
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
