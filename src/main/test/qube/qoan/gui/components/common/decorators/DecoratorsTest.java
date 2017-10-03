/*
 * Copyright 2017 Qoan Software Association. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package qube.qoan.gui.components.common.decorators;

import qube.qai.services.implementation.SearchResult;
import qube.qoan.services.QoanTestBase;

/**
 * Created by rainbird on 7/8/17.
 */
public class DecoratorsTest extends QoanTestBase {

    public void testHistogramDeocrator() throws Exception {

        Decorator decorator = new HistogramDecorator();

        SearchResult result = new SearchResult("context", "title", "uuid", "test search", 1.0);
        decorator.decorate(result);

        fail("this test is not yet implemented");
    }

    public void testImageDecorator() throws Exception {

        Decorator decorator = new ImageDecorator();

        SearchResult result = new SearchResult("context", "title", "uuid", "test search", 1.0);
        decorator.decorate(result);

        fail("this test is not yet implemented");
    }

    public void testMolecularViewerDecorator() throws Exception {

        Decorator decorator = new MolecularViewerDecorator();

        SearchResult result = new SearchResult("context", "title", "uuid", "test search", 1.0);
        decorator.decorate(result);

        fail("this test is not yet implemented");
    }

    public void testNetworkDecorator() throws Exception {

        Decorator decorator = new NetworkDecorator();

        SearchResult result = new SearchResult("context", "title", "uuid", "test search", 1.0);
        decorator.decorate(result);

        fail("this test is not yet implemented");
    }

    public void testPdfFileDecorator() throws Exception {

        Decorator decorator = new PdfFileDecorator();

        SearchResult result = new SearchResult("context", "title", "uuid", "test search", 1.0);
        decorator.decorate(result);

        fail("this test is not yet implemented");
    }

    public void testTimeSeriesDecorator() throws Exception {

        Decorator decorator = new StockQuotesDecorator();

        SearchResult result = new SearchResult("context", "title", "uuid", "test search", 1.0);
        decorator.decorate(result);

        fail("this test is not yet implemented");
    }

    public void testWikiDecorator() throws Exception {

        Decorator decorator = new WikiDecorator();

        SearchResult result = new SearchResult("context", "title", "uuid", "test search", 1.0);
        decorator.decorate(result);

        fail("this test is not yet implemented");
    }
}
