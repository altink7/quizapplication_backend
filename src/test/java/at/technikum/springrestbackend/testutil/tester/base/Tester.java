package at.technikum.springrestbackend.testutil.tester.base;

/**
 * This annotation interface defines a tester for classes and methods functionality.
 * Test methods annotated with this should take an instance parameter for testing.
 */
public interface Tester {
    void test(Object instance);
}
