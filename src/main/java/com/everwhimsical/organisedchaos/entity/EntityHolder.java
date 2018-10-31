package com.everwhimsical.organisedchaos.entity;

import com.everwhimsical.organisedchaos.utility.Commons;
import java.util.List;
import java.util.Map;

public class EntityHolder {

    private final Object value;

    EntityHolder(Object value) {
        this.value = value;
    }

    public Object toObject() {
        return this.value;
    }

    public String asString() {
        nullCheck();
        return (String) this.value;
    }

    public int toInteger() {
        nullCheck();
        if (value.getClass().equals(Long.class)) {
            return ((Long) value).intValue();
        } else if (value.getClass().equals(String.class)) {
            return Integer.parseInt((String) value);
        } else if (value.getClass().equals(Double.class)) {
            Double myVar = Double.valueOf(value + "");
            return myVar.intValue();
        }
        return (int) value;
    }

    public long toLong() {
        nullCheck();
        if (value.getClass().equals(Integer.class)) {
            Integer myVar = Integer.parseInt(value + "");
            return myVar.longValue();
        } else if (value.getClass().equals(String.class)) {
            return Long.parseLong((String) value);
        }
        return (long) value;
    }

    public float toFloat() {
        nullCheck();
        if (value.getClass().equals(Double.class)) {
            return ((Double) value).floatValue();
        } else if (value.getClass().equals(String.class)) {
            return Float.parseFloat((String) value);
        }
        return (float) value;
    }

    public double toDouble() {
        nullCheck();
        if (value.getClass().equals(Float.class)) {
            Float myVar = Float.parseFloat(value + "");
            return myVar.doubleValue();
        } else if (value.getClass().equals(String.class)) {
            return Double.parseDouble((String) value);
        }
        return (long) value;
    }

    public boolean toBoolean() {
        nullCheck();
        if (value.getClass().equals(String.class)) {
            return Boolean.parseBoolean((String) value);
        }
        return (Boolean) value;
    }

    @SuppressWarnings("unchecked")
    public <T extends List<?>> T toList() {
        nullCheck();
        return (T) value;
    }

    @SuppressWarnings("unchecked")
    public <T extends Map<?, ?>> T toMap() {
        nullCheck();
        return (T) value;
    }

    private void nullCheck() {
        Commons.checkNotNull(value, "Value cannot be null");
    }
}
