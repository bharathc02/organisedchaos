package com.everwhimsical.organisedchaos.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.util.regex.Pattern;

class EntityOperations {

    static final String INVALID_JSON_XPATH = "Invalid JSON xpath passed.";
    static final String INVALID_JSON_REPLACE = "Invalid JSON replace object passed.";
    private static final String REGEX_JSON_ARRAY_INDEX = "\\[\\d*]$";
    private static final Pattern PATTERN = Pattern.compile(REGEX_JSON_ARRAY_INDEX);

    static boolean bothOfSameType(JsonElement first, JsonElement second) {
        if (first == null || second == null) {
            return false;
        }
        boolean bothArrays = first.isJsonArray() && second.isJsonArray();
        boolean bothNulls = first.isJsonNull() && second.isJsonNull();
        boolean bothObjects = first.isJsonObject() && second.isJsonObject();
        boolean bothPrimitives = first.isJsonPrimitive() && second.isJsonPrimitive();
        return bothArrays || bothNulls || bothObjects || bothPrimitives;
    }

    static JsonElement parse(Object value) {
        Gson gson = new GsonBuilder().create();
        String rawText = gson.toJson(value);
        return gson.fromJson(rawText, JsonElement.class);
    }

    static boolean isArrayIndex(String token) {
        return PATTERN.matcher(token).find();
    }

    static String asText(String text) {
        if (text.endsWith("]")) {
            return PATTERN.matcher(text).replaceAll("");
        }
        return text;
    }

    static int asDigit(String text) {
        if (isArrayIndex(text) && text.endsWith("]")) {
            text = text.substring(text.indexOf("[") + 1, text.lastIndexOf("]"));
            return Integer.parseInt(text);
        }
        return -1;
    }
}
