package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.OwnerDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OwnersDAO  {

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
    private final static String SELECT_BY_ID = "SELECT * FROM cars WHERE id = %d;";
    private final static String ADD_CAR = "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('%s', %d, %d)";
    private final static String UPDATE_CAR = "UPDATE cars SET brand = '%s', year_of_produce = %d, " +
        "net_worth = %d WHERE id = %d;";
    private final static String DELETE_BY_ID = "DELETE FROM cars WHERE id=%d;";

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

}