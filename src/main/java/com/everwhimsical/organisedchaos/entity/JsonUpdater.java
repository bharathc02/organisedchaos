package com.everwhimsical.organisedchaos.entity;

import static com.everwhimsical.organisedchaos.entity.EntityOperations.asDigit;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.asText;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.bothOfSameType;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.isArrayIndex;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.parse;

import com.everwhimsical.organisedchaos.ChaosException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

class JsonUpdater {

    private JsonUpdater() {
        //Utility class. Defeat instantiation.
    }

    static EntityHolder update(String jsonData, String xpathKey, Object jsonValue) {
        int index = xpathKey.lastIndexOf(".");
        String parent = null;
        if (index != -1) {
            parent = xpathKey.substring(0, index);
        }
        String child = xpathKey.substring(index + 1, xpathKey.length());
        String json = update(jsonData, parent, child, jsonValue).toString();
        return new EntityHolder(json);
    }

    private static JsonElement update(String jsonString, String parent, String child,
        Object jsonValue) {
        JsonElement parsedJson = new JsonParser().parse(jsonString);
        JsonElement jsonCopyForNavigation = parsedJson;

        if (null == parent) {
            updateLeaf(parsedJson, child, jsonValue);
            return parsedJson;
        }

        //If we are here, then it means that the input xPath contains one or more elements which have
        //to be navigated before we reach our child.

        //Split the parent into one or more parent parts.
        String[] parts = parent.split("\\Q.\\E");
        for (String part : parts) {
            if (!isArrayIndex(part)) {
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonObject().get(part);
                continue;
            }
            //If a part ends with "]" then its representing an array.
            //So lets extract out the index from within the "[]" text.
            int upto = asDigit(part);
            if (part.equals("[" + upto + "]")) {
                //It seems that only for root elements we can represent a parent
                //as [0].child.bar.
                //So lets handle that condition.
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonArray().get(upto);
            } else {
                String text = asText(part);
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonObject().get(text)
                    .getAsJsonArray().get(upto);
            }

        }
        updateLeaf(jsonCopyForNavigation, child, jsonValue);
        return parsedJson;
    }

    private static void updateLeaf(JsonElement jsonCopyForNavigation, String key, Object value) {
        JsonElement actual = parse(value);
        if (isArrayIndex(key)) {
            int index = asDigit(key);
            String node = asText(key);
            //handle conditions wherein child looks like [0]
            if (("[" + index + "]").equals(key)) {
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonArray();
            } else {
                //child looks something like foo[1]
                //So we know for sure that the input json would be a json object which contains
                //a key called "foo" whose value is a json array. So lets fetch that.
                jsonCopyForNavigation = jsonCopyForNavigation.getAsJsonObject().get(node);
            }

            if (!jsonCopyForNavigation.isJsonArray()) {
                throw new ChaosException("Did not find a Json array for " + key);
            }
            if (index > jsonCopyForNavigation.getAsJsonArray().size()) {
                throw new ChaosException("Invalid index provided in  " + key);
            }

            //We were asked to be cognitive of the types. So first check if the element
            //at the index is of the same type as the value. And only if they both are of
            //the same types, inject the new value, else throw error and bail out.
            JsonElement current = jsonCopyForNavigation.getAsJsonArray().get(index);
            if (!bothOfSameType(current, actual)) {
                throw new ChaosException(EntityOperations.INVALID_JSON_REPLACE);
            }

            jsonCopyForNavigation.getAsJsonArray().set(index, actual);
            return;
        }

        //If we are here then it means that child is a regular element such as "foo" and doesnt
        //have any array indexes.
        JsonElement current = jsonCopyForNavigation.getAsJsonObject().get(key);
        if (current == null) {
            throw new ChaosException(EntityOperations.INVALID_JSON_XPATH);
        }
        if (!bothOfSameType(current, actual)) {
            throw new ChaosException(EntityOperations.INVALID_JSON_REPLACE);
        }

        jsonCopyForNavigation.getAsJsonObject().add(key, actual);
    }

}