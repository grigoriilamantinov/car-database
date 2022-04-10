package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.OwnerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnersDAO {

    private ConnectionFactory connectionFactory;

    public OwnersDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private final static String SELECT_ALL_OWNER = "SELECT * FROM owners;";
    private final static String STATEMENT_JOIN = "SELECT " +
        "cars.id AS Car_id, cars.brand AS Brand, cars.net_worth AS Cost, " +
        "cars.year_of_produce AS Year, owners.first_name AS First_name, owners.last_name " +
        "AS Last_name FROM owners " +
        "LEFT JOIN cars ON owners.car_id = cars.id " +
        "WHERE owners.id = %d;";

    public List<OwnerDTO> findAll () {
        Connection connection = connectionFactory.connectionOpen();
        List<OwnerDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_OWNER);
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
        List<CarDTO> carOwners = new ArrayList<>();
        OwnerDTO owner = this.getById(ownerId);
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement =
                connection.prepareStatement(String.format(STATEMENT_JOIN, ownerId));
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
        Connection connection = connectionFactory.connectionOpen();
        OwnerDTO owner = new OwnerDTO();
        try {
            PreparedStatement statement =
                connection.prepareStatement(String.format("SELECT * FROM owners WHERE id = %d;", id));
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