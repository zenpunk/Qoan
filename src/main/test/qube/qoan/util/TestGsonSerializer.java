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
import com.thoughtworks.xstream.XStream;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import qube.qai.main.QaiConstants;
import qube.qai.services.implementation.SearchResult;
import qube.qai.services.implementation.UUIDService;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
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

        Vector<SearchResult> dummyResults = createResultVector(100);
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

    public void testGsonSerializationWithSets() throws Exception {

        Gson gson = new GsonBuilder().create();

        Set<SearchResult> resultSet = createResultSet(100);
        String asGsonString = gson.toJson(resultSet);

        assertTrue("there must be a string", StringUtils.isNoneEmpty(asGsonString));

        Type setType = new TypeToken<Set<SearchResult>>() {
        }.getType();
        Set<SearchResult> deserialList = gson.fromJson(asGsonString, setType);

        assertNotNull("there has to be result", deserialList);
        assertTrue("the results must not be empty", deserialList.size() > 0);
        assertTrue("the result lists must be of same length", resultSet.size() == deserialList.size());

        for (SearchResult result : resultSet) {
            assertTrue("the has to be the same results", deserialList.contains(result));
        }
    }

    public void testMixedCases() throws Exception {

        XStream xStream = new XStream();
        Set<SearchResult> resultSet = createResultSet(1);
        assertTrue("size has to be one", resultSet.size() == 1);

        String serialSetString = xStream.toXML(resultSet);
        assertNotNull(serialSetString);

        Object deserialSet = xStream.fromXML(serialSetString);
        assertNotNull(deserialSet);
        assertTrue(deserialSet instanceof Set);
    }

    public void testGsonSerializationWithUtilityClass() throws Exception {

        Set<SearchResult> resultSet = createResultSet(100);
        String asGsonString = GsonSerializer.serializeSet(resultSet);

        assertTrue("there must be a string", StringUtils.isNoneEmpty(asGsonString));

        Set<SearchResult> deserialList = GsonSerializer.deserializeSet(asGsonString);

        assertNotNull("there has to be result", deserialList);
        assertTrue("the results must not be empty", deserialList.size() > 0);
        assertTrue("the result lists must be of same length", resultSet.size() == deserialList.size());

        for (SearchResult result : resultSet) {
            assertTrue("the has to be the same results", deserialList.contains(result));
        }
    }

    private Set<SearchResult> createResultSet(int number) {
        Set<SearchResult> results = new HashSet<>();

        for (int i = 0; i < number; i++) {
            SearchResult result = new SearchResult(WIKIPEDIA, "dummy result " + i, UUIDService.uuidString(), "result with number " + i, 1.0);
            results.add(result);
        }

        return results;
    }

    private Vector<SearchResult> createResultVector(int number) {
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
