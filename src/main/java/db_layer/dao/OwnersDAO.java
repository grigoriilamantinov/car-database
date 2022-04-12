package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.OwnerDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnersDAO {

    private final ConnectionFactory connectionFactory;
    private String dataSource;

    public OwnersDAO(ConnectionFactory connectionFactory, String dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    public List<OwnerDTO> findAll () {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        List<OwnerDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectAllOwners());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(OwnerDTO.of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    public List<CarDTO> getCarOwners(int ownerId) {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        List<CarDTO> carOwners = new ArrayList<>();
        OwnerDTO owner = this.getById(ownerId);
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement =
                connection.prepareStatement(String.format(loader.getStatementSelectCarOnOwner(), ownerId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carOwners.add(
                    new CarDTO()
                        .id(resultSet.getInt("Car_id"))
                        .brand(resultSet.getString("Brand"))
                        .owner(owner)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);
        return carOwners;
    }

    public OwnerDTO getById(int id) {
        PropertiesLoader loader = new PropertiesLoader(dataSource);
        Connection connection = connectionFactory.connectionOpen();
        OwnerDTO owner = new OwnerDTO();
        try {
            PreparedStatement statement =
                connection.prepareStatement(String.format(loader.getStatementSelectOwnerById(), id));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            owner = OwnerDTO.builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .idCar(resultSet.getInt("car_id"))
                .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);

        return owner;
    }
}