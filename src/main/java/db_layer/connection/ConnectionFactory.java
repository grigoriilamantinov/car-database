package db_layer.connection;

import db_layer.propertiesLoader.PropertiesLoader;
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
            Class.forName(loader.getDriver());
            connection = DriverManager.getConnection(
                    loader.getDbUrl(),
                    loader.getUser(),
                    loader.getPassword()
            );
        } catch (SQLException | ClassNotFoundException e) {
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
