package db_layer.tableCreator;

import db_layer.connection.ConnectionFactory;
import db_layer.propertiesLoader.PropertiesLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TableCreator {
    private final ConnectionFactory connectionFactory;
    private final String filePath;

    public TableCreator(ConnectionFactory connectionFactory, String filePath) {
        this.connectionFactory = connectionFactory;
        this.filePath = filePath;
    }

    public void dropAllTables() {
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS car_into_shops;DROP TABLE IF EXISTS cars;DROP TABLE IF EXISTS owners;DROP TABLE IF EXISTS car_shops;");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
    }

    public void createAllTables() {
        PropertiesLoader loader = new PropertiesLoader(filePath);
        StringBuilder sb = new StringBuilder();
        File file = new File(loader.getCreateStateAllTables());
        Connection connection = connectionFactory.connectionOpen();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            PreparedStatement statement = connection.prepareStatement(sb.toString());
            statement.execute();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
    }

}
