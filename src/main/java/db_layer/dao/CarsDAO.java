package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarShopsDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarsDAO  implements DAO<CarDTO> {

    private final ConnectionFactory connectionFactory;
    private final String dataSource;

    public CarsDAO(ConnectionFactory connectionFactory, String dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public List<CarDTO> findAll() {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        List<CarDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectCars());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(CarDTO.of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    @Override
    public CarDTO getById(int id) {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                String.format(loader.getStatementSelectCarById(),id)
            );
            resultSet = statement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);
        return CarDTO.of(resultSet);
    }

    public void deleteCarFromShop(int carId, int shopId) {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement(
                String.format(loader.getStatementDelFromCarShop(), carId, shopId));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Строчка удалена!");
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
    }

    public List<CarShopsDTO> carInParticularShop (int carId) {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        List<CarShopsDTO> carIntoShop = new ArrayList<>();

        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement =
                connection.prepareStatement(
                    String.format(loader.getStatementSelectCarShop(), carId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carIntoShop.add(
                    CarShopsDTO.builder()
                        .brand(resultSet.getString("brand"))
                        .shop(resultSet.getString("shop"))
                        .build()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);

        return carIntoShop;
    }
}
