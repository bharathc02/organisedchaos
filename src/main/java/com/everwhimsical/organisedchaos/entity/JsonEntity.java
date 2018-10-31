package com.everwhimsical.organisedchaos.entity;

import com.everwhimsical.organisedchaos.utility.Reader;

public class JsonEntity {

    private String jsonData;

    private JsonEntity(String jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * JSON Entity Builder
     *
     * @param filePath The JSON file path as string
     * @return Returns {@link JsonEntity} builder
     */
    public static JsonEntity file(String filePath) {
        return string(Reader.text(filePath));
    }

    /**
     * JSON Entity Builder
     *
     * @param jsonData The JSON data as string
     * @return Returns {@link JsonEntity} builder
     */
    public static JsonEntity string(String jsonData) {
        return new JsonEntity(jsonData);
    }

    /**
     * Used to get data in the JSON using the key passed.
     *
     * @param xpathKey is the xpath of the element to be retrieved.
     * @return Returns {@link EntityHolder} for the xpath key.
     */
    public EntityHolder get(String xpathKey) {
        return JsonRetriever.get(jsonData, xpathKey);
    }

    /**
     * Used to remove data in the JSON using the key passed.
     *
     * @param xpathKey is the xpath of the element to be removed.
     * @return Returns {@link JsonEntity} builder
     */
    public JsonEntity remove(String xpathKey) {
        this.jsonData = JsonRemover.remove(jsonData, xpathKey).asString();
        return this;
    }

    /**
     * Used to insert data to the JSON using the key and value passed.
     *
     * @param xpathKey is the xpath of the element to be inserted.
     * @param jsonValue Value to be inserted.
     * @return Returns {@link JsonEntity} builder
     */
    public JsonEntity insert(String xpathKey, Object jsonValue) {
        this.jsonData = JsonInserter.insert(jsonData, xpathKey, jsonValue).asString();
        return this;
    }

    /**
     * Used to update the JSON using the key and value passed.
     *
     * @param xpathKey is the xpath of the element to be updated.
     * @param jsonValue Value to be updated.
     * @return Returns {@link JsonEntity} builder
     */
    public JsonEntity update(String xpathKey, Object jsonValue) {
        this.jsonData = JsonUpdater.update(jsonData, xpathKey, jsonValue)
            .asString();
        return this;
    }

    /**
     * Fetch the entity after performing needed operations.
     *
     * @return Returns {@link EntityHolder}.
     */
    public EntityHolder get() {
        return new EntityHolder(jsonData);
    }
}
