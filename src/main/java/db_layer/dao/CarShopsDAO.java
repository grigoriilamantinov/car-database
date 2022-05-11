package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarShopsDTO;
import db_layer.logger.LoggerManager;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarShopsDAO {
    private final ConnectionFactory connectionFactory;
    private final PropertiesLoader loader;
    String DAO_MESSAGE = "Error %s trying execute SQL query: %s";

    public CarShopsDAO(
        final ConnectionFactory connectionFactory,
        final PropertiesLoader loader
    ) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

//    private final static Logger logger = Logger.getLogger(CarShopsDAO.class.getName());
    public List<CarShopsDTO> findAll() {
        final Connection connection = connectionFactory.openConnection();
        final List<CarShopsDTO> result = new ArrayList<>();
        final String sqlStatement = String.format(loader.getStatementSelectCarShopById());
        try {
            final PreparedStatement statement = connection.prepareStatement(sqlStatement);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(CarShopsDTO.of(resultSet));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(DAO_MESSAGE, sqlStatement));
        } finally {
            connectionFactory.closeConnection(connection);
        }
        return result;
    }
}
