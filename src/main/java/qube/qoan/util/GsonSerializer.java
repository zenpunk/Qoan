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
import qube.qai.services.implementation.SearchResult;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * Created by rainbird on 6/29/17.
 */
public class GsonSerializer {

    public static String serializeSet(Set<SearchResult> resultSet) {

        Gson gson = new GsonBuilder().create();
        return gson.toJson(resultSet);
    }

    public static SearchResult deserialize(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, SearchResult.class);
    }

    public static Set<SearchResult> deserializeSet(String jsonSet) {

        Gson gson = new GsonBuilder().create();
        Type setType = new TypeToken<Set<SearchResult>>() {
        }.getType();
        return gson.fromJson(jsonSet, setType);
    }
}
