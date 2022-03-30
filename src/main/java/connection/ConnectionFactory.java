package connection;

import propertiesLoader.PropertiesLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private String dataSource;

    public ConnectionFactory(String dataSource) {
        this.dataSource = dataSource;
    }

    public Connection connectionOpen() {
        Connection connection = null;
        try {
            PropertiesLoader loader = new PropertiesLoader(dataSource);
            connection = DriverManager.getConnection(
                    loader.getDbUrl(),
                    loader.getUser(),
                    loader.getPassword()
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
}
