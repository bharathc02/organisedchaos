package com.everwhimsical.organisedchaos.entity;

import io.restassured.path.json.JsonPath;

public class JsonRetriever {

    private JsonRetriever() {
        //Utility class. Defeat instantiation.
    }

    static EntityHolder get(String jsonData, String xpathKey) {
        Object json = JsonPath.with(jsonData).get(xpathKey);
        return new EntityHolder(json);
    }
}
