package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private String dataSource;

    public ConnectionFactory(String dataSource) {
        this.dataSource = dataSource;
        this.loadDataSourceProperties();
    }

    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String DB_URL = "db_url";
    private Properties properties = new Properties();

    public Connection connectionOpen() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty(DB_URL),
                    properties.getProperty(USER),
                    properties.getProperty(PASSWORD)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void connectionClose(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
}
