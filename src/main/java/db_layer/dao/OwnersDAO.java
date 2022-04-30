package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.OwnerDTO;
import db_layer.logger.LoggerManager;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnersDAO {

    String DAO_MESSAGE = "Error %s trying execute SQL query: %s";
    private final ConnectionFactory connectionFactory;
    private final PropertiesLoader loader;

    public OwnersDAO(
        final ConnectionFactory connectionFactory,
        final  PropertiesLoader loader) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

    public List<OwnerDTO> findAll () {
        final Connection connection = connectionFactory.openConnection();
        final List<OwnerDTO> result = new ArrayList<>();
        final String sqlStatement = String.format(loader.getStatementSelectAllOwners());
        try {
            final PreparedStatement statement = connection.prepareStatement(sqlStatement);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(OwnerDTO.of(resultSet));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DAO_MESSAGE, sqlStatement));
        } finally {
            connectionFactory.closeConnection(connection);
        }
        return result;
    }

    public OwnerDTO getById(int id) {
        final Connection connection = connectionFactory.openConnection();
        final String sqlStatement = String.format(loader.getStatementSelectOwnerById());
        OwnerDTO owner = new OwnerDTO();
        try {
            final PreparedStatement statement =
                connection.prepareStatement(sqlStatement, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                owner = OwnerDTO.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .idCar(resultSet.getInt("car_id"))
                    .build();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DAO_MESSAGE, sqlStatement));
        }
        connectionFactory.closeConnection(connection);

        return owner;
    }
}