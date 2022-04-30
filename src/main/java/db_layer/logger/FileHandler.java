package db_layer.logger;

import java.io.IOException;

public class FileHandler {

    public static java.util.logging.FileHandler getFileHandler() {
        java.util.logging.FileHandler fileHandler = null;
        try {
            fileHandler = new java.util.logging.FileHandler("src/main/resources/log.txt", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileHandler;
    }
}
