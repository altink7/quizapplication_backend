package at.technikum.springrestbackend.testutil.tester.standard;


import at.technikum.springrestbackend.testutil.tester.base.AbstractClassTester;
import at.technikum.springrestbackend.testutil.tester.base.Tester;

import java.lang.reflect.Method;

/**
 * Utility class to test the beginning of toString() method of a class.
 * Usage: ToStringTester.testToStringBeginning(Example.class);
 * Replace Example. Class with the class you want to test.
 * <p>
 * This utility class tests whether the beginning of the toString() method of a given class
 * starts with the class name followed by a '{'. It can be used to quickly check the basic
 * formatting of the toString() implementation.
 * </p>
 * <p>
 * Note: The utility class handles both enums and regular classes.
 * </p>
 */
public class ToStringTester extends AbstractClassTester implements Tester {

    /**
     * Tests the beginning of the toString() method of the specified class.
     *
     * @param instance The class to test.
     */
    @Override
    public void test(Object instance) {
        Class<?> clazz = instance.getClass();
        try {
            Method toStringMethod = getMethod(clazz, "toString");

            if (toStringMethod == null) {
                return; // No 'toString' method, nothing to test
            }

            Object instanceTest = createInstance(clazz);

            String expectedBeginning = clazz.getSimpleName() + "{";

            // Invoke the toString() method and check if it starts with the expected beginning
            String actualToString = (String) toStringMethod.invoke(instanceTest);
            if (!actualToString.startsWith(expectedBeginning)) {
                throw new AssertionError("Beginning of toString() method for class " + clazz.getSimpleName() + " failed.");
            } else {
                System.out.println("toString() beginning test passed for class " + clazz.getSimpleName());
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Error testing toString() beginning for class " + clazz.getSimpleName(), e);
        }
    }
}

