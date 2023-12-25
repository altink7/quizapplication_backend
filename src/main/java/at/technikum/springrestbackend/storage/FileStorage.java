package at.technikum.springrestbackend.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * FileStorage interface
 */
public interface FileStorage {

    /**
     * Upload file to storage
     * @param file multipart file
     * @return id of file
     */
    String upload(MultipartFile file);

    /**
     * Load file from storage
     * @param id id of file
     * @return input stream of file
     */
    InputStream load(String id);

}
