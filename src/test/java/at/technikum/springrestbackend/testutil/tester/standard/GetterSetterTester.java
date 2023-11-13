package at.technikum.springrestbackend.testutil.tester.standard;


import at.technikum.springrestbackend.testutil.tester.base.AbstractClassTester;
import at.technikum.springrestbackend.testutil.tester.base.Tester;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Utility class to test getters and setters of a class.
 * Use instanceSupplier from ModelTester if you do not have the default Constructor
 */
public class GetterSetterTester extends AbstractClassTester implements Tester {

    @Override
    public void test(Object instance) {
        Class<?> clazz = instance.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (isGetter(method)) {
                testGetter(method, clazz);
            } else if (isSetter(method)) {
                testSetter(method, clazz);
            }
        }
    }

    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0 && !method.getReturnType().equals(void.class);
    }

    private static boolean isSetter(Method method) {
        return method.getName().startsWith("set") && method.getParameterCount() == 1;
    }

    private static void testGetter(Method getter, Class<?> clazz) {
        String propertyName = getPropertyName(getter);
        try {
            Method setter = clazz.getDeclaredMethod("set" + propertyName, getter.getReturnType());
            Object sampleValue = createSampleValue(getter.getReturnType());
            Object instance = clazz.getDeclaredConstructor().newInstance();

            setter.invoke(instance, sampleValue);
            Object actualValue = getter.invoke(instance);

            assert sampleValue != null;
            if (!sampleValue.equals(actualValue)) {
                throw new AssertionError("Getter and Setter for property " + propertyName + " failed.");
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("Error testing getter and setter for property " + propertyName, e);
        }
    }

    private static void testSetter(Method setter, Class<?> clazz) {
        String propertyName = getPropertyName(setter);
        try {
            //for boolean properties, the getter may be "isProperty" instead of "getProperty"
            Method getter = getMethod(setter, clazz, propertyName);
            Object sampleValue = createSampleValue(setter.getParameterTypes()[0]);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            setter.invoke(instance, sampleValue);
            Object actualValue = getter.invoke(instance);

            assert sampleValue != null;

            if (!sampleValue.equals(actualValue)) {
                throw new AssertionError("Setter and Getter for property " + propertyName + " failed.");
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("Error testing setter and getter for property " + propertyName, e);
        }
    }

    private static Method getMethod(Method setter, Class<?> clazz, String propertyName) throws NoSuchMethodException {
        Method getter;
        if (setter.getParameterTypes()[0] == boolean.class || setter.getParameterTypes()[0] == Boolean.class) {
            try {
                getter = clazz.getDeclaredMethod("is" + propertyName);
            } catch (NoSuchMethodException e) {
                getter = clazz.getDeclaredMethod("get" + propertyName);
            }
        } else {
            getter = clazz.getDeclaredMethod("get" + propertyName);
        }
        return getter;
    }

    private static String getPropertyName(Method method) {
        String methodName = method.getName();
        if (methodName.startsWith("get") || methodName.startsWith("is")) {
            return methodName.substring(methodName.startsWith("get") ? 3 : 2); // Remove "get" or "is" prefix
        }
        if (methodName.startsWith("set")) {
            return methodName.substring(3); // Remove "set" prefix
        }
        throw new IllegalArgumentException("Method is not a valid getter: " + methodName);
    }

    private static Object createSampleValue(Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return 42;
        } else if (type == String.class) {
            return "sampleString";
        } else if (type == boolean.class || type == Boolean.class) {
            return true;
        } else if (type == long.class || type == Long.class) {
            return 123456789L;
        } else if (type == double.class || type == Double.class) {
            return 3.14;
        } else if (type == float.class || type == Float.class) {
            return 2.71f;
        } else if (type == char.class || type == Character.class) {
            return 'A';
        } else if (type == byte.class || type == Byte.class) {
            return (byte) 8;
        } else if (type == short.class || type == Short.class) {
            return (short) 16;
        } else if (type == byte[].class) {
            return new byte[]{1, 2, 3};
        } else if (type == int[].class) {
            return new int[]{4, 5, 6};
        } else if (type == String[].class) {
            return new String[]{"array", "of", "strings"};
        } else if (type == File.class) {
            return new File("test.txt");
        } else if(type == LocalDateTime.class) {
            return LocalDate.now().atStartOfDay();
        } else if (type.isEnum()) {
            Object[] enumValues = type.getEnumConstants();
            if (enumValues.length > 0) {
                return enumValues[0];
            }
        } else if (Collection.class.isAssignableFrom(type)) {
            if (HashSet.class.isAssignableFrom(type)) {
                return new HashSet<>();
            } else if (ArrayList.class.isAssignableFrom(type)) {
                return new ArrayList<>();
            } else {
                return new ArrayList<>();
            }
        } else if (type == LocalDate.class) {
            return LocalDate.now();
        } else if (!type.isPrimitive()) {
            try {
                Constructor<?> noArgConstructor = type.getDeclaredConstructor();
                if (noArgConstructor.getParameterCount() == 0) {
                    return noArgConstructor.newInstance();
                } else {
                    throw new IllegalArgumentException("Type has no-arg constructor but cannot be instantiated: " + type.getName());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }



}