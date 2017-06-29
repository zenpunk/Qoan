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

package qube.qoan.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junit.framework.TestCase;
import qube.qai.main.QaiConstants;
import qube.qai.services.implementation.SearchResult;
import qube.qai.services.implementation.UUIDService;

import java.lang.reflect.Type;
import java.util.Vector;

/**
 * Created by rainbird on 6/29/17.
 */
public class TestGsonSerializer extends TestCase implements QaiConstants {

    public void testGsonSearchResult() {
        Gson gson = new GsonBuilder().create();

        SearchResult result = new SearchResult(WIKIPEDIA, "dummy search", UUIDService.uuidString(), "dummy description", 1.0);

        String gsonString = gson.toJson(result);
        log("the object as string: '" + gsonString + "'");

        SearchResult serialResult = gson.fromJson(gsonString, SearchResult.class);
        assertNotNull("there has to be something", serialResult);
        assertTrue("the objects must be same", serialResult.equals(result));

        Vector<SearchResult> dummyResults = createResults(100);
        String serializedList = gson.toJson(dummyResults);
        Type listType = new TypeToken<Vector<SearchResult>>() {
        }.getType();
        Vector<SearchResult> deserialList = gson.fromJson(serializedList, listType);

        assertNotNull("there has to be a result", deserialList);
        assertTrue("the results must not be empty", deserialList.size() > 0);
        assertTrue("the result lists must be of same length", dummyResults.size() == deserialList.size());

        for (int i = 0; i < dummyResults.size(); i++) {
            SearchResult di = dummyResults.get(i);
            SearchResult dj = deserialList.get(i);
            assertTrue("the objects must be the same", di.equals(dj));
        }
    }

    private Vector<SearchResult> createResults(int number) {
        Vector<SearchResult> results = new Vector<>();

        for (int i = 0; i <= number; i++) {
            SearchResult result = new SearchResult(WIKIPEDIA, "dummy result " + i, UUIDService.uuidString(), "result with number " + i, 1.0);
            results.add(result);
        }

        return results;
    }

    private void log(String message) {
        System.out.println(message);
    }
}
