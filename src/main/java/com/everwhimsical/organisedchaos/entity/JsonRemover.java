package com.everwhimsical.organisedchaos.entity;

import static com.everwhimsical.organisedchaos.entity.EntityOperations.asDigit;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.asText;
import static com.everwhimsical.organisedchaos.entity.EntityOperations.isArrayIndex;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

class JsonRemover {

    private JsonRemover() {
        //Utility class. Defeat instantiation.
    }

    static EntityHolder remove(String jsonData, String xpathKey) {
        //First check if there's a parent in the key.
        int index = xpathKey.lastIndexOf(".");
        String parent = null;
        if (index != -1) {
            parent = xpathKey.substring(0, index);
        }
        String child = xpathKey.substring(index + 1, xpathKey.length());
        String json = remove(jsonData, parent, child).toString();
        return new EntityHolder(json);
    }

    private static JsonElement remove(String jsonString, String parent, String key) {
        JsonElement json = new JsonParser().parse(jsonString);
        if (null == parent) {
            //We didn't get any parent. So blindly delete the leaf alone.
            removeLeaf(json, key);
            return json;
        }
        JsonElement jsonObject = json;
        //Split the parent into one or more parent parts.
        String[] parts = parent.split("\\Q.\\E");
        boolean root = true;
        for (String part : parts) {
            if (isArrayIndex(part)) {
                //If a part ends with "]" then its representing an array.
                //So lets extract out the index from within the "[]" text.
                int upto = asDigit(part);
                if (root && part.equals("[" + upto + "]")) {
                    //It seems that only for root elements we can represent a parent
                    //as [0].child.bar.
                    //So lets handle that condition.
                    jsonObject = jsonObject.getAsJsonArray().get(upto);
                } else {
                    String text = asText(part);
                    jsonObject = jsonObject.getAsJsonObject().get(text).getAsJsonArray().get(upto);
                }
                root = false;
                continue;
            }
            jsonObject = jsonObject.getAsJsonObject().get(part);
        }
        removeLeaf(jsonObject, key);
        return json;
    }

    private static void removeLeaf(JsonElement json, String key) {
        if (isArrayIndex(key)) {
            int upto = asDigit(key);
            String k = asText(key);
            json.getAsJsonObject().get(k).getAsJsonArray().remove(upto);
        } else {
            json.getAsJsonObject().remove(key);
        }
    }

}