package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarIntoShopsDTO;
import db_layer.dto.ShopDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopsDAO implements DAO{

    private final ConnectionFactory connectionFactory;

    public ShopsDAO (ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private final static String SELECT_ALL_SHOPS = "SELECT * FROM car_shops;";
    private final static String SELECT_ALL_CAR_INTO_SHOP = "SELECT car_id," +
        "cars.brand, car_shops.shop_id, car_shops.shop " +
        "FROM car_into_shops " +
        "LEFT JOIN cars ON cars.id = car_into_shops.car_id " +
        "LEFT JOIN car_shops ON car_into_shops.id_shop = car_shops.shop_id " +
        "WHERE shop_id = %d;";

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List findAll() {
        Connection connection = connectionFactory.connectionOpen();
        List<ShopDTO> result = new ArrayList<>();
        ShopDTO dto = new ShopDTO();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SHOPS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(dto.of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    public List<CarIntoShopsDTO> allCarInParticularShop (int shopId) {
        List<CarIntoShopsDTO> carIntoShop = new ArrayList<>();

        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement =
                connection.prepareStatement(String.format(SELECT_ALL_CAR_INTO_SHOP, shopId));
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
