package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarShopsDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarShopsDAO {
    private final ConnectionFactory connectionFactory;
    private final String dataSource;

    public CarShopsDAO(ConnectionFactory connectionFactory, String dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    public List<CarShopsDTO> findAll() {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        List<CarShopsDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectCarShopById());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(CarShopsDTO.of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }
}
