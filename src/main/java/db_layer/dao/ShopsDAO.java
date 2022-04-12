package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarIntoShopsDTO;
import db_layer.dto.ShopDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopsDAO implements DAO<ShopDTO>{

    private final ConnectionFactory connectionFactory;
    private String dataSource;

    public ShopsDAO(ConnectionFactory connectionFactory, String dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public ShopDTO getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<ShopDTO> findAll() {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        List<ShopDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectAllShops());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(ShopDTO.of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    public List<CarIntoShopsDTO> allCarInOneShop(int shopId) {

        List<CarIntoShopsDTO> carIntoShop = new ArrayList<>();
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement =
                connection.prepareStatement(String.format(loader.getStatementSelectCarJoinOneShop(), shopId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carIntoShop.add(
                    CarIntoShopsDTO.builder()
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
