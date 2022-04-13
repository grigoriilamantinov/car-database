package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarShopsDTO;
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
        final Connection connection = connectionFactory.connectionOpen();
        final List<CarDTO> result = new ArrayList<>();
        try {
            final PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectCars());
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(CarDTO.of(resultSet));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    @Override
    public CarDTO getById(final int id) {
        final Connection connection = connectionFactory.connectionOpen();
        ResultSet resultSet = null;
        try {
            final PreparedStatement statement = connection.prepareStatement(
                String.format(loader.getStatementSelectCarById(),id)
            );
            resultSet = statement.executeQuery();
            resultSet.next();
        } catch (final SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);
        return CarDTO.of(resultSet);
    }

    public void deleteCarFromShop(final int carId, final int shopId) {
        final Connection connection = connectionFactory.connectionOpen();
        try {
            final PreparedStatement statement = connection.prepareStatement(
                String.format(loader.getStatementDelFromCarShop(), carId, shopId));
            statement.execute();
        } catch (final SQLException e) {
            System.out.println("Строчка удалена!");
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
    }

    public List<CarShopsDTO> carInParticularShop (int carId) {
        final List<CarShopsDTO> carIntoShop = new ArrayList<>();

        final Connection connection = connectionFactory.connectionOpen();
        try {
            final PreparedStatement statement =
                connection.prepareStatement(
                    String.format(loader.getStatementSelectCarShop(), carId));
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
        } connectionFactory.connectionClose(connection);
        return carIntoShop;
    }
}
