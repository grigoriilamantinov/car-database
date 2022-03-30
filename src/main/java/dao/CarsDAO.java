package dao;

import connection.ConnectionFactory;
import dto.CarsDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarsDAO implements DAO {

    private ConnectionFactory connectionFactory;

    public CarsDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void createTable() {
        StringBuilder sb = new StringBuilder();
        File file = new File(
                "C:\\Users\\grigorii\\IdeaProjects\\car_database\\src\\main\\resources\\statement_for_create_table_cars"
        );
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

    @Override
    public List<CarsDTO> findAll() {
        Connection connection = connectionFactory.connectionOpen();
        List<CarsDTO> result = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(String.format("SELECT * FROM cars;"));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarsDTO dto = new CarsDTO();
                dto.setId(resultSet.getInt("id"));
                dto.setBrand(resultSet.getString("brand"));
                dto.setYear(resultSet.getInt("year_of_produce"));
                dto.setCost(resultSet.getInt("net_worth"));
                result.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    @Override
    public CarsDTO getById(int id) {
        CarsDTO dto = new CarsDTO();
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement(String.format("SELECT * FROM cars WHERE id = %d;",id));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            dto.setId(resultSet.getInt("id"));
            dto.setBrand(resultSet.getString("brand"));
            dto.setYear(resultSet.getInt("year_of_produce"));
            dto.setCost(resultSet.getInt("net_worth"));
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);
        return dto;
    }

    @Override
    public void addString(CarsDTO usersObject) {
        String brandName = usersObject.getBrand();
        int year = usersObject.getYear();
        int cost = usersObject.getCost();
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement(String.format(
                    "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('%s', %d, %d)", brandName, year, cost
                    ));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
    }

    @Override
    public void update(CarsDTO usersData) {
        int id = usersData.getId();
        String brand = usersData.getBrand();
        int year = usersData.getYear();
        int cost = usersData.getCost();
        Connection connection = connectionFactory.connectionOpen();{
            try {
                PreparedStatement statement = connection.prepareStatement(String.format(
                        "UPDATE cars SET brand = '%s', year_of_produce = %d, net_worth = %d WHERE id = %d;",brand, year, cost, id
                ));
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionFactory.connectionClose(connection);
        }
    }

    @Override
    public void deleteById(int id) {
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement(String.format("DELETE FROM cars WHERE id=%d;", id));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
        System.out.print("\nСтрочка " + id + " удалена\n");
    }

    @Override
    public void dropTable() {
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS cars;");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
    }
}
