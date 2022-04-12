package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarIntoShopsDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarIntoShopsDAO implements DAO {
    private ConnectionFactory connectionFactory;
    private String dataSource;

    public CarIntoShopsDAO(ConnectionFactory connectionFactory, String dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List findAll() {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        List<CarIntoShopsDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectCarShopById());
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
