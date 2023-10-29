package at.technikum.springrestbackend.testutil.tester.base;

import java.lang.reflect.Method;

/**
 * Holds Basic Implementing for all Testers
 */
public abstract class AbstractClassTester {

    /**
     * Creates an instance of the given class.
     *
     * @param clazz The class to create an instance of.
     * @return An instance of the class.
     */
    protected static Object createInstance(Class<?> clazz) {
        try {
            if (clazz.isEnum()) {
                return clazz.getEnumConstants()[0];
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Error creating instance of class " + clazz.getSimpleName(), e);
        }
    }

    /**
     * Retrieves the given method of the given class.
     *
     * @param clazz The class to retrieve the toString() method from.
     * @param method method to be tested, for example, toString
     * @return The given method or null if not found.
     */
    @SuppressWarnings("SameParameterValue")
    protected static Method getMethod(Class<?> clazz, String method) {
        try {
            return clazz.getDeclaredMethod(method);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
