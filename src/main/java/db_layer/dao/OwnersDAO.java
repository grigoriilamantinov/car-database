package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.OwnerDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OwnersDAO {

    private final ConnectionFactory connectionFactory;
    private final PropertiesLoader loader;

    public OwnersDAO(
        final ConnectionFactory connectionFactory,
        final  PropertiesLoader loader) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

    private final static Logger logger = Logger.getLogger(CarsDAO.class.getName());
    public List<OwnerDTO> findAll () {
        final Connection connection = connectionFactory.connectionOpen();
        final List<OwnerDTO> result = new ArrayList<>();
        try {
            final PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectAllOwners());
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(OwnerDTO.of(resultSet));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            logger.info("Товарищ, что-то не так в запросе при обращении к таблице");
        } finally {
            connectionFactory.closeConnection(connection);
        }
        return result;
    }

    public OwnerDTO getById(int id) {
        final Connection connection = connectionFactory.connectionOpen();
        OwnerDTO owner = new OwnerDTO();
        try {
            final PreparedStatement statement =
                connection.prepareStatement(String.format(loader.getStatementSelectOwnerById(), id));
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                owner = OwnerDTO.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .idCar(resultSet.getInt("car_id"))
                    .build();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            logger.info("Товарищ, что-то не так в запросе при обращении к таблице");
        }
        connectionFactory.closeConnection(connection);

        return owner;
    }
}