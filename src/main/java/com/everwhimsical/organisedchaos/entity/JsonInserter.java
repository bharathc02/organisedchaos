package com.everwhimsical.organisedchaos.entity;

import static com.everwhimsical.organisedchaos.entity.EntityOperations.asDigit;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.asText;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.isArrayIndex;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.parse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class JsonInserter {

    private JsonInserter() {
        //Utlity class. Defeat instantiation.
    }

    static EntityHolder insert(String jsonData, String xpathKey, Object jsonValue) {
        int index = xpathKey.lastIndexOf(".");
        String parent = null;
        if (index != -1) {
            parent = xpathKey.substring(0, index);
        }
        String child = xpathKey.substring(index + 1, xpathKey.length());
        //Extract out the parent and child by splitting the xpathkey by the last
        //occurrence of "."
        String json = insert(jsonData, parent, child, jsonValue).toString();
        return new EntityHolder(json);
    }

    private static JsonElement insert(String jsonString, String parent, String key, Object value) {
        JsonElement parsedJson = new JsonParser().parse(jsonString);
        if (null == parent) {
            //We didn't get any parent. So append without a parent.
            appendLeaf(parsedJson, key, value);
            return parsedJson;
        }
        JsonElement jsonCopyForNavigation = parsedJson;
        //Split the parent into one or more parent parts.
        String[] parts = parent.split("\\Q.\\E");
        for (String part : parts) {
            if (!isArrayIndex(part)) {
                if (!jsonCopyForNavigation.getAsJsonObject().has(part)) {
                    jsonCopyForNavigation.getAsJsonObject().add(part, new JsonObject());
                }
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonObject().get(part);
                continue;
            }

            //If a part ends with "]" then its representing an array.
            //So lets extract out the index from within the "[]" text.
            int index = asDigit(part);
            if (part.equals("[" + index + "]")) {
                //It seems that only for root elements we can represent a parent
                //as [0].child.bar.
                //So lets handle that condition.
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonArray().get(index);
                continue;
            }
            String keyWithoutIndexes = asText(part);
            if (!jsonCopyForNavigation.getAsJsonObject().has(keyWithoutIndexes)) {
                JsonArray array = new JsonArray();
                jsonCopyForNavigation.getAsJsonObject().add(keyWithoutIndexes, array);
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonObject()
                    .get(keyWithoutIndexes).getAsJsonArray();
                continue;
            }
            JsonArray array = jsonCopyForNavigation.getAsJsonObject().get(keyWithoutIndexes)
                .getAsJsonArray();
            if (index > array.size()) {
                throw new IllegalArgumentException("The insertion location " +
                    keyWithoutIndexes + " is way past the size.");
            }

            if (index < array.size()) {
                jsonCopyForNavigation = array.get(index);
            } else {
                array.add(new JsonObject());
                jsonCopyForNavigation = array.get(array.size() - 1);
            }
        }
        appendLeaf(jsonCopyForNavigation, key, value);
        return parsedJson;
    }

    private static void appendLeaf(JsonElement jsonCopyForNavigation, String key, Object value) {
        JsonElement jsonElement = parse(value);
        if (isArrayIndex(key)) {
            String k = asText(key);
            JsonArray original;
            if (jsonCopyForNavigation.isJsonObject()) {
                original = Optional.ofNullable(jsonCopyForNavigation.getAsJsonObject().get(k))
                    .map(JsonElement::getAsJsonArray)
                    .orElse(new JsonArray());
            } else {
                original = jsonCopyForNavigation.getAsJsonArray();
            }
            appendArrayToLeaf(jsonCopyForNavigation, original, jsonElement, key);
            return;
        }
        if (jsonCopyForNavigation.isJsonObject()) {
            if (jsonCopyForNavigation.getAsJsonObject().has(key)) {
                JsonElement element = jsonCopyForNavigation.getAsJsonObject().get(key);
                if (element.isJsonArray()) {
                    element.getAsJsonArray().add(jsonElement);
                } else {
                    //Overwrite the existing key's value with the map being provided to us.
                    jsonCopyForNavigation.getAsJsonObject().add(key, jsonElement);
                }
            } else {
                jsonCopyForNavigation.getAsJsonObject().add(key, jsonElement);
            }
        } else {
            appendArrayToLeaf(jsonCopyForNavigation, jsonCopyForNavigation.getAsJsonArray(),
                jsonElement, key);
        }
    }

    private static void appendArrayToLeaf(JsonElement jsonCopyForNavigation, JsonArray original,
        JsonElement value, String key) {
        if (!isArrayIndex(key)) {
            if (jsonCopyForNavigation.isJsonArray()) {
                JsonObject object = new JsonObject();
                object.add(key, value);
                jsonCopyForNavigation.getAsJsonArray().add(object);
                return;
            }
        }
        int index = asDigit(key);
        String keyWithoutIndexes = asText(key);
        if (index > original.size()) {
            throw new IllegalArgumentException("The insertion location " +
                keyWithoutIndexes + " is way past the size.");
        }
        JsonArray cloned = new JsonArray();
        List<JsonElement> list = new LinkedList<>();
        original.forEach(list::add);
        list.add(index, value);
        list.forEach(cloned::add);
        if (jsonCopyForNavigation.isJsonObject()) {
            jsonCopyForNavigation.getAsJsonObject().add(keyWithoutIndexes, cloned);
            return;
        }
        Iterator iterator = jsonCopyForNavigation.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        jsonCopyForNavigation.getAsJsonArray().addAll(cloned);
    }

}
