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
    private final PropertiesLoader loader;

    public OwnersDAO(
        final ConnectionFactory connectionFactory,
        final  PropertiesLoader loader) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

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
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    public List<CarDTO> getOwnersCar(final int ownerId) {
        final List<CarDTO> carOwners = new ArrayList<>();
        final OwnerDTO owner = this.getById(ownerId);
        final Connection connection = connectionFactory.connectionOpen();
        try {
            final PreparedStatement statement =
                connection.prepareStatement(String.format(loader.getStatementSelectCarOnOwner(), ownerId));
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carOwners.add(
                    new CarDTO()
                        .id(resultSet.getInt("Car_id"))
                        .brand(resultSet.getString("Brand"))
                        .owner(owner)
                );
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);
        return carOwners;
    }

    public OwnerDTO getById(int id) {
        final Connection connection = connectionFactory.connectionOpen();
        OwnerDTO owner = new OwnerDTO();
        try {
            final PreparedStatement statement =
                connection.prepareStatement(String.format(loader.getStatementSelectOwnerById(), id));
            final ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            owner = OwnerDTO.builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .idCar(resultSet.getInt("car_id"))
                .build();

        } catch (final SQLException e) {
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);

        return owner;
    }
}