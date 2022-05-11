package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarShopsDTO;
import db_layer.logger.LoggerManager;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarsDAO implements DAO<CarDTO> {

    private final ConnectionFactory connectionFactory;
    private final PropertiesLoader loader;

    public CarsDAO(
        final ConnectionFactory connectionFactory,
        final PropertiesLoader loader
    ) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

    @Override
    public List<CarDTO> findAll() {
        final Connection connection = connectionFactory.openConnection();
        final List<CarDTO> result = new ArrayList<>();
        final String sqlStatement = String.format(loader.getStatementSelectCars());
        try {
            final PreparedStatement statement = connection.prepareStatement(sqlStatement);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(CarDTO.of(resultSet));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DAO_MESSAGE, sqlStatement));
        } finally {
            connectionFactory.closeConnection(connection);
        }
        return result;
    }

    @Override
    public CarDTO getById(final int id) {
        final Connection connection = connectionFactory.openConnection();
        final String sqlStatement = String.format(loader.getStatementSelectCarById(), id);
        ResultSet resultSet;
        CarDTO carDTO = new CarDTO();
        try {
            final PreparedStatement statement = connection.prepareStatement(
                String.format(sqlStatement)
            );
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carDTO = CarDTO.of(resultSet);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DAO_MESSAGE, sqlStatement));
        }
        connectionFactory.closeConnection(connection);
        return carDTO;
    }

    public void deleteCarFromShop(final int carId, final int shopId) {
        final Connection connection = connectionFactory.openConnection();
        final String sqlStatement = String.format(loader.getStatementDelFromCarShop(), carId, shopId);
        try {
            final PreparedStatement statement = connection.prepareStatement(
                String.format(sqlStatement));
            statement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DAO_MESSAGE, sqlStatement));
        }
        connectionFactory.closeConnection(connection);
    }

    public List<CarShopsDTO> carInParticularShop (int carId) {
        final List<CarShopsDTO> carIntoShop = new ArrayList<>();

        final Connection connection = connectionFactory.openConnection();
        final String sqlStatement = String.format(loader.getStatementSelectCarShop(), carId);
        try {
            final PreparedStatement statement =
                connection.prepareStatement(
                    String.format(sqlStatement));
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carIntoShop.add(
                    CarShopsDTO.builder()
                        .brand(resultSet.getString("brand"))
                        .shop(resultSet.getString("shop"))
                        .build()
                );
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DAO_MESSAGE, sqlStatement));
        }
        connectionFactory.closeConnection(connection);
        return carIntoShop;
    }
}
