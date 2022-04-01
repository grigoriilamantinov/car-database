package propertiesLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    private String dataSource;

    public PropertiesLoader(String dataSource) {
        this.dataSource = dataSource;
        this.loadDataSourceProperties();
    }

    private final static String CREATE_STATE = "createCars";
    private final static String MENU = "menu";
    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String DB_URL = "db_url";

    private Properties properties = new Properties();

    private void loadDataSourceProperties() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(dataSource);
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCreateState() {
        return properties.getProperty(CREATE_STATE);
    }

    public String getMenu() {
        return properties.getProperty(MENU);
    }

    public String getUser() {
        return properties.getProperty(USER);
    }

    public String getPassword() {
        return properties.getProperty(PASSWORD);
    }

    public String getDbUrl() {
        return properties.getProperty(DB_URL);
    }
}
