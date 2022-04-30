package db_layer.connection;

import db_layer.propertiesLoader.PropertiesLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private final PropertiesLoader propertiesLoader;

    public ConnectionFactory(final PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    public Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName(propertiesLoader.getDriver());
            connection = DriverManager.getConnection(
                propertiesLoader.getDbUrl(),
                propertiesLoader.getUser(),
                propertiesLoader.getPassword()
            );
        } catch (final SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(final Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
