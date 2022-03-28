import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TableCars {
    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String DB_URL = "db_url";
    public static String TILDE = "~";

    Connection connection;
    PreparedStatement statement;

    Properties properties = this.getDataForConnect();

    public void createTableCars() {
        this.connectionOpen();
        try {
            this.statementForCreateTable().execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String outputData() {
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

    public void insertString(String usersInput) {
        String[] usersInputArray;
        usersInputArray = usersInput.split(TILDE);
        String brandName = usersInputArray[0];
        Integer year = Integer.parseInt(usersInputArray[1]);
        Integer cost = Integer.parseInt(usersInputArray[2]);
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

    public void deleteString(int id) {
        this.connectionOpen();
        try {
            statement = connection.prepareStatement(String.format("DELETE FROM cars WHERE id=%d;", id));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionClose();
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

    private PreparedStatement statementForCreateTable() {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS cars (\n" +
                            "    id SERIAL NOT NULL,\n" +
                            "    brand VARCHAR NOT NULL,\n" +
                            "    year_of_produce INT NOT NULL,\n" +
                            "    net_worth INT NOT NULL\n" +
                            ");" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Maddyson', 1984,2000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Honda', 1999,2500000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Lada', 2006,900000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('GAZ', 2010,1000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Lada', 1999,700000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Lada', 2007,3000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('BMW', 2018,7000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Ferrari', 2020,12000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Honda', 2017,3000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Yo-mobil', 2010,9990000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Banshi', 1990,1000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Opel', 2014,2055000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Toyota', 2012,6799999);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Opel', 2018,5000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Honda', 2015,5000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Honda', 2009,2756000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Toyota', 2015,2990000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Ferrari', 2022,52000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Lada', 1965,1000000);" +
                            "INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Honda', 1999,2190000);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
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
