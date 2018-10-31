package com.everwhimsical.organisedchaos.utility.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionHelper {

    public static Object invokeNoArgumentMethod(String methodName, Object objectToInvokeUpon) {
        try {
            Method method = objectToInvokeUpon.getClass().getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(objectToInvokeUpon);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionInvocationException(e);
        }
    }

    public static Object invokeNoArgumentStaticMethod(String methodName, Class<?> clazz) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionInvocationException(e);
        }
    }

    private static class ReflectionInvocationException extends RuntimeException {

        ReflectionInvocationException(Throwable t) {
            super(t);
        }
    }
}
