package com.everwhimsical.organisedchaos.model;

import java.util.List;

/**
 * Webservice houses the configuration for a Rest/Soap call.
 */
public class Webservice {

    private String clientName;
    private String clientVersion;
    private String webserviceType;
    private String requestType;
    private String requestUrl;
    private String description;
    private HeaderCluster requestHeader;
    private String requestPayload;
    private int responseCode;
    private HeaderCluster responseHeader;
    private String responseContent;
    private List<Object> resourceMetaDataList;
    private long elapsedTime;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getWebserviceType() {
        return webserviceType;
    }

    public void setWebserviceType(String webserviceType) {
        this.webserviceType = webserviceType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HeaderCluster getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(HeaderCluster requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public HeaderCluster getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(HeaderCluster responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public List<Object> getResourceMetaDataList() {
        return resourceMetaDataList;
    }

    public void setResourceMetaDataList(List<Object> resourceMetaDataList) {
        this.resourceMetaDataList = resourceMetaDataList;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
