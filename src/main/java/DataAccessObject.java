import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DataAccessObject {

    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String DB_URL = "db_url";

    Connection connection;
    PreparedStatement statement;
    Properties properties = this.getDataForConnect();

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

        this.connectionOpen();

        try {
            PreparedStatement statement = connection.prepareStatement(sb.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionClose();
    }

    public String formatToStringFullTable() {
        this.connectionOpen();
        String line = "";
        StringBuilder sb = new StringBuilder();
        try {
            statement = connection.prepareStatement(String.format("SELECT * FROM cars;"));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer carId = resultSet.getInt("id");
                String brand = resultSet.getString("brand");
                Integer yearOfProduce = resultSet.getInt("year_of_produce");
                Integer netWorth = resultSet.getInt("net_worth");
                line = String.format("%d: %s %d %d \n", carId, brand, yearOfProduce, netWorth);
                sb.append(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionClose();
        }
        return sb.toString();
    }

    public TransportObjectData formatForOutputOneString(int id) {
        TransportObjectData stringFromTable = new TransportObjectData(0,"0",0,0);
        this.connectionOpen();
        try {
            statement = connection.prepareStatement(String.format("SELECT * FROM cars WHERE id = %d;",id));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            stringFromTable.setId(resultSet.getInt("id"));
            stringFromTable.setBrand(resultSet.getString("brand"));
            stringFromTable.setYear(resultSet.getInt("year_of_produce"));
            stringFromTable.setCost(resultSet.getInt("net_worth"));
        } catch (SQLException e) {
            e.printStackTrace();
        } connectionClose();
        return stringFromTable;
    }

    public void addString(TransportObjectData usersObject) {
        String brandName = usersObject.getBrand();
        int year = usersObject.getYear();
        int cost = usersObject.getCost();
        this.connectionOpen();
        try {
            statement = connection.prepareStatement(String.format(
                    "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('%s', %d, %d)", brandName, year, cost
                    ));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionClose();
    }

    public void updateString(TransportObjectData usersData) {
        int id = usersData.getId();
        String brand = usersData.getBrand();
        int year = usersData.getYear();
        int cost = usersData.getCost();
        this.connectionOpen();{
            try {
                statement = connection.prepareStatement(String.format(
                        "UPDATE cars SET brand = '%s', year_of_produce = %d, net_worth = %d WHERE id = %d;",brand, year, cost, id
                ));
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionClose();
        }
    }

    public void deleteString(int id) {
        this.connectionOpen();
        try {
            statement = connection.prepareStatement(String.format("DELETE FROM cars WHERE id=%d;", id));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionClose();
        System.out.print("\nСтрочка " + id + " удалена\n");
    }

    public void dropTable() {
        this.connectionOpen();
        try {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS cars;");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionClose();
        }
    }

    private void connectionOpen() {
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty(DB_URL),
                    properties.getProperty(USER),
                    properties.getProperty(PASSWORD)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectionClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Properties getDataForConnect() {
        Properties properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream("C:\\Users\\grigorii\\IdeaProjects\\LearningSQL\\src\\main\\resources\\connection.properties");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
