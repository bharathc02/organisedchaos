package com.everwhimsical.organisedchaos.utility;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DataStore {

    private static ThreadLocal<Map<String, Object>> storeThread = ThreadLocal
        .withInitial(LinkedHashMap::new);

    private DataStore() {

    }

    private static Map<String, Object> getStore() {
        return storeThread.get();
    }

    public static void put(String key, Object value) {
        getStore().put(key, value);
    }

    public static Object get(String key) {
        return getStore().get(key);
    }

    public static Object getString(String key) {
        Object obj = getStore().get(key);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static void clearStore() {
        getStore().clear();
    }

    public static void delete(String key) {
        getStore().remove(key);
    }

    public static boolean isKeyPresent(String key) {
        return getStore().containsKey(key);
    }

    public static Set<String> getKeySet() {
        return getStore().keySet();
    }

    public static Object pop(String key) {
        return getStore().remove(key);
    }
}
