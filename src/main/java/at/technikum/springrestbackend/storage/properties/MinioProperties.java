package at.technikum.springrestbackend.storage.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class MinioProperties {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.port}")
    private int port;

    @Value("${minio.user}")
    private String user;

    @Value("${minio.password}")
    private String password;

    @Value("${minio.bucket}")
    private String bucket;
}
