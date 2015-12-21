package qube.qoan.gui.workspace.finance.parser;

import junit.framework.TestCase;
import qube.qai.persistence.WikiArticle;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.SearchResult;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Created by rainbird on 12/21/15.
 */
public class TestWikiTableVisitor extends QoanTestBase {

    @Inject
    @Named("Wikipedia_en")
    private SearchServiceInterface searchService;

    /**
     * this is mainly for testing Wiki-tables which are then will be
     * converted to stock-quotes
     * @throws Exception
     */
    public void testWikiTableParser() throws Exception {

        // List of companies by stock exchange listing
        String listArticleName = "List of companies by stock exchange listing";
        Collection<SearchResult> results = searchService.searchInputString(listArticleName, "title", 100);
        for (SearchResult result : results) {

        }
    }
}
