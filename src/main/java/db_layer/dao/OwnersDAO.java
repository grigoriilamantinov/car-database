package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.OwnerDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OwnersDAO {

    private ConnectionFactory connectionFactory;
    private String filePath;
    private int id;
    private String firstName;
    private String lastName;


    public OwnersDAO(ConnectionFactory connectionFactory, String filePath) {
        this.connectionFactory = connectionFactory;
        this.filePath = filePath;
    }

    public OwnersDAO() {
    }

    public OwnersDAO(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private final static String SELECT_ALL = "SELECT * FROM owners;";
    private final static String STATEMENT_JOIN = "SELECT " +
        "cars.id AS Car_id, cars.brand AS Brand, cars.net_worth AS Cost, " +
        "cars.year_of_produce AS Year, owners.first_name AS First_name, owners.last_name " +
        "AS Last_name FROM owners " +
        "LEFT JOIN cars ON owners.id = cars.owner_id " +
        "WHERE owners.id = %d;";

    public void createTable() {
        PropertiesLoader loader = new PropertiesLoader(filePath);
        StringBuilder sb = new StringBuilder();
        File file = new File(loader.getCreateStateOwners());
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = connectionFactory.connectionOpen();

        try {
            PreparedStatement statement = connection.prepareStatement(sb.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);
    }

    public List<OwnerDTO> findAll () {
        Connection connection = connectionFactory.connectionOpen();
        List<OwnerDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
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

    public void dropTable () {
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS owners;");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }

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
                .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);

        return owner;
    }
}