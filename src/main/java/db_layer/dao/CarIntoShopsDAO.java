package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarIntoShopsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarIntoShopsDAO implements DAO {
    private ConnectionFactory connectionFactory;

    public CarIntoShopsDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    private final static String SELECT_ALL_CAR_INTO_SHOPS = "SELECT * FROM car_into_shops;";

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
        List<CarIntoShopsDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CAR_INTO_SHOPS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(CarIntoShopsDTO.of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }
}
