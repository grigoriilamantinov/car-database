package db_layer.logger;

import java.util.logging.Logger;

public class LoggerManager {
    public static Logger getLogger() {
        Logger logger = Logger.getLogger("CarDatabaseLog");
        logger.addHandler(FileHandler.getFileHandler());
        return logger;
    }
}
