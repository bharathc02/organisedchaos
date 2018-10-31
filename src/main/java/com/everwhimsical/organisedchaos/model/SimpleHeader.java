package com.everwhimsical.organisedchaos.model;

import com.everwhimsical.organisedchaos.utility.Commons;
import java.util.Objects;

public class SimpleHeader {

    private final String name;
    private final String value;

    /**
     * Constructor with name and value
     *
     * @param name the header name
     * @param value the header value
     */
    public SimpleHeader(final String name, final String value) {
        this.name = Commons.checkNotNull(name, "Header Name");
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public boolean containsHeaderKey(SimpleHeader header) {
        if (this == header) {
            return true;
        }
        return Objects.equals(getName(), header.getName());
    }

    @Override
    public String toString() {
        return "SimpleHeader{" +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleHeader)) {
            return false;
        }
        SimpleHeader header = (SimpleHeader) o;
        return Objects.equals(getName(), header.getName()) &&
            Objects.equals(getValue(), header.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }
}
