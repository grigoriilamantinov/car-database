package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarIntoShopsDTO;
import db_layer.dto.OwnerDTO;
import db_layer.dto.ShopDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarsDAO  implements DAO<CarDTO> {

    private final ConnectionFactory connectionFactory;

    public CarsDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private final static String SELECT_ALL = "SELECT * FROM cars;";
    private final static String SELECT_BY_ID = "SELECT * FROM cars WHERE id = %d;";
    private final static String ADD_CAR = "INSERT INTO cars (brand, year_of_produce, net_worth) "
                                        + "VALUES ('%s', %d, %d)";
    private final static String UPDATE_CAR = "UPDATE cars SET brand = '%s', year_of_produce = %d, " +
                                             "net_worth = %d WHERE id = %d;";
    private final static String DELETE_BY_ID = "DELETE FROM cars WHERE id = %d;";
    private final static String DELETE_CAR_INTO_SHOP = "DELETE FROM car_into_shops WHERE car_id = %d and id_shop = %d;";
    private final static String SELECT_CAR_INTO_SHOP = "SELECT car_id," +
        "cars.brand, car_shops.shop_id, car_shops.shop " +
        "FROM car_into_shops " +
        "LEFT JOIN cars ON cars.id = car_into_shops.car_id " +
        "LEFT JOIN car_shops ON car_into_shops.id_shop = car_shops.shop_id " +
        "WHERE car_id = %d;";


    @Override
    public List<CarDTO> findAll() {
        Connection connection = connectionFactory.connectionOpen();
        List<CarDTO> result = new ArrayList<>();
        CarDTO dto = new CarDTO();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(dto.of(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.connectionClose(connection);
        }
        return result;
    }

    @Override
    public CarDTO getById(int id) {
        Connection connection = connectionFactory.connectionOpen();
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(String.format(SELECT_BY_ID,id));
            resultSet = statement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);
        return CarDTO.of(resultSet);
    }

    public void save(CarDTO usersObject) {
        String brandName = usersObject.getBrand();
        int year = usersObject.getYear();
        int cost = usersObject.getCost();
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement(
                String.format(ADD_CAR, brandName, year, cost)
            );
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
    }

    public void update(CarDTO usersData) {
        int id = usersData.getId();
        String brand = usersData.getBrand();
        int year = usersData.getYear();
        int cost = usersData.getCost();
        Connection connection = connectionFactory.connectionOpen();{
            try {
                PreparedStatement statement = connection.prepareStatement(String.format(
                    UPDATE_CAR, brand, year, cost, id
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
            PreparedStatement statement = connection.prepareStatement(String.format(DELETE_BY_ID, id));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Строчка удалена!");
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
        System.out.print("\nСтрочка " + id + " удалена\n");
    }

    public void deleteCarFromShop(int carId, int shopId) {
        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement(String.format(DELETE_CAR_INTO_SHOP, carId, shopId));
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Строчка удалена!");
            e.printStackTrace();
        }
        connectionFactory.connectionClose(connection);
    }

    public List<CarIntoShopsDTO> carInParticularShop (int carId) {
        List<CarIntoShopsDTO> carIntoShop = new ArrayList<>();

        Connection connection = connectionFactory.connectionOpen();
        try {
            PreparedStatement statement =
                connection.prepareStatement(String.format(SELECT_CAR_INTO_SHOP, carId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carIntoShop.add(
                    CarIntoShopsDTO.builder()
                        .brand(resultSet.getString("brand"))
                        .shop(resultSet.getString("shop"))
                        .build()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionFactory.connectionClose(connection);

        return carIntoShop;
    }
}
