package db_layer.dao;

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
    private final PropertiesLoader loader;

    public TableCreator(
        final ConnectionFactory connectionFactory,
        final PropertiesLoader loader
    ) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

    public void dropAllTables() {
        final Connection connection = connectionFactory.connectionOpen();
        try {
            final PreparedStatement statement = connection.prepareStatement(loader.getStatementDropAllTables());
            statement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
    }

    public void prepareAllTables() {
        final StringBuilder sb = new StringBuilder();
        final File file = new File(loader.getCreateStateAllTables());
        final Connection connection = connectionFactory.connectionOpen();
        try {
            final Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            final PreparedStatement statement = connection.prepareStatement(sb.toString());
            statement.execute();
        } catch (final FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
//        connectionFactory.connectionClose(connection);
    }
}
