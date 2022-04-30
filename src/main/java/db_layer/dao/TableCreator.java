package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.logger.LoggerManager;
import db_layer.propertiesLoader.PropertiesLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TableCreator {
    private final ConnectionFactory connectionFactory;
    private PropertiesLoader loader;

    public TableCreator(
        final ConnectionFactory connectionFactory,
        final PropertiesLoader loader
    ) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

    String CREATE_MESSAGE_LOG = "Error %s trying execute SQL query create_all_table";
    String DROP_MESSAGE_LOG = "Error %s trying execute SQL query drop_all_table";

    public void dropAllTables() {
        final Connection connection = connectionFactory.openConnection();
        final String sqlStatement = String.format(loader.getStatementDropAllTables());
        try {
            final PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DROP_MESSAGE_LOG));

        } finally {
            connectionFactory.closeConnection(connection);
        }
    }

    public void prepareAllTables() {
        final StringBuilder sb = new StringBuilder();
        final File file = new File(loader.getCreateStateAllTables());
        final Connection connection = connectionFactory.openConnection();
        try {
            final Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            final PreparedStatement statement = connection.prepareStatement(sb.toString());
            statement.execute();
        } catch (final FileNotFoundException | SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(CREATE_MESSAGE_LOG));
        }
    }
}
