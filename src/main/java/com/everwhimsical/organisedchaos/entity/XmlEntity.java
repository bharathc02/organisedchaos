package com.everwhimsical.organisedchaos.entity;

import com.everwhimsical.organisedchaos.utility.Reader;
import io.restassured.path.xml.XmlPath;

public class XmlEntity {

    private String xmlData;

    private XmlEntity(String xmlData) {
        this.xmlData = xmlData;
    }

    /**
     * XML Entity Builder
     *
     * @param filePath The XML data as string
     * @return Returns {@link XmlEntity} builder
     */
    public static XmlEntity file(String filePath) {
        return string(Reader.text(filePath));
    }

    /**
     * XML Entity Builder
     *
     * @param xmlData The XML data as string
     * @return {@link XmlEntity} builder.
     */
    public static XmlEntity string(String xmlData) {
        return new XmlEntity(xmlData);
    }

    /**
     * Used to remove data in the XML using the key passed.
     *
     * @param xpathKey is the xpath of the element to be retrieved.
     * @return Returns {@link XmlEntity} builder.
     */
    public XmlEntity get(String xpathKey) {
        Object xml = XmlPath.with(xmlData).get(xpathKey);
        EntityHolder xmlEntity = new EntityHolder(xml);
        this.xmlData = xmlEntity.asString();
        return this;
    }

    public EntityHolder get() {
        return new EntityHolder(xmlData);
    }
}
