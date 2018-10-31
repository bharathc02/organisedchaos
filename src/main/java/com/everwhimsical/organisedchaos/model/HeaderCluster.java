package com.everwhimsical.organisedchaos.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HeaderCluster {

    private List<SimpleHeader> headerList = new ArrayList<>();

    public HeaderCluster() {

    }

    public List<SimpleHeader> getAllHeaders() {
        return headerList;
    }

    public void add(String name, String value) {
        headerList.add(new SimpleHeader(name, value));
    }

    public void add(SimpleHeader header) {
        headerList.add(header);
    }

    public void remove(String name) {
        headerList.removeIf(header -> header.getName().equalsIgnoreCase(name));
    }

    public void remove(SimpleHeader header) {
        headerList.remove(header);
    }

    public boolean uniqueMerge(List<SimpleHeader> mergeHeaderList) {
        boolean isAdded = false;
        for (SimpleHeader mergeHeader : mergeHeaderList) {
            boolean skip = false;
            for (SimpleHeader header : headerList) {
                if (header.containsHeaderKey(mergeHeader)) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                headerList.add(mergeHeader);
                isAdded = true;
            }
        }
        return isAdded;
    }

    public SimpleHeader getFirstHeader(final String name) {
        for (SimpleHeader header : this.headerList) {
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    public SimpleHeader getLastHeader(final String name) {
        // start at the end of the list and work backwards
        for (int i = headerList.size() - 1; i >= 0; i--) {
            SimpleHeader header = this.headerList.get(i);
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    public List<SimpleHeader> getHeaders(final String name) {
        List<SimpleHeader> headersFound = new ArrayList<>();
        for (final SimpleHeader header : this.headerList) {
            if (header.getName().equalsIgnoreCase(name)) {
                headersFound.add(header);
            }
        }
        return headersFound;
    }

    public SimpleHeader getCondensedHeader(final String name) {
        List<SimpleHeader> hdrs = getHeaders(name);
        if (hdrs.size() == 0) {
            return null;
        } else if (hdrs.size() == 1) {
            return hdrs.get(0);
        } else {
            final StringBuilder valueBuffer = new StringBuilder(128);
            valueBuffer.append(hdrs.get(0).getValue());
            for (int i = 1; i < hdrs.size(); i++) {
                valueBuffer.append(", ");
                valueBuffer.append(hdrs.get(i).getValue());
            }
            return new SimpleHeader(name.toLowerCase(Locale.ROOT), valueBuffer.toString());
        }
    }

    @Override
    public String toString() {
        return "HeaderCluster{" +
            "headerList=" + headerList +
            '}';
    }
}
