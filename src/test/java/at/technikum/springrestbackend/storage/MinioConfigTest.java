package at.technikum.springrestbackend.storage;

import at.technikum.springrestbackend.storage.properties.MinioProperties;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MinioConfigTest {

    @Test
    public void testMinioClientBean() {
        MinioProperties minioProperties = Mockito.mock(MinioProperties.class);
        Mockito.when(minioProperties.getUrl()).thenReturn("http://localhost");
        Mockito.when(minioProperties.getPort()).thenReturn(9000);
        Mockito.when(minioProperties.getUser()).thenReturn("user");
        Mockito.when(minioProperties.getPassword()).thenReturn("password");

        MinioConfig minioConfig = new MinioConfig(minioProperties);

        assertNotNull(minioConfig.minioClient());
    }

}