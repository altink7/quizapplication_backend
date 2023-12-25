package at.technikum.springrestbackend.storage.properties;

import at.technikum.springrestbackend.testutil.ModelTester;
import org.junit.jupiter.api.Test;

class MinioPropertiesTest {
    @Test
    void testGetterSetter() {
        ModelTester.forClass(MinioProperties.class).test();
    }
}