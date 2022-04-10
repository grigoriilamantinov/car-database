package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
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
}
