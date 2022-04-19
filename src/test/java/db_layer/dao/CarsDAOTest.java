package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.propertiesLoader.PropertiesLoader;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.TabableView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarsDAOTest {

    private static final String user = "user";
    private static final String password = "1234";
    private static final String url = "jdbc:h2:mem:testdb";
    private static final String driverName = "org.h2.Driver";

    PropertiesLoader loader = new PropertiesLoader(
        "C:\\Users\\grigorii\\IdeaProjects\\car_database\\" +
        "src\\main\\resources\\test.properties"
    );
    ConnectionFactory factory = new ConnectionFactory(loader);
    TableCreator tableCreator = new TableCreator(factory, loader);

    @BeforeEach
    void prepareData() {
        tableCreator.prepareAllTables();
    }

    @Test
    void findAll() {
        CarsDAO carsDAO = new CarsDAO(factory, loader);
        final List<CarDTO> exceptedResult = new ArrayList<>() {{
            add(new CarDTO(1, "Maddyson", 1984, 2000000));
            add(new CarDTO(2, "Lada", 2006, 900000));
            add(new CarDTO(3, "GAZ", 2010, 1000000));
            add(new CarDTO(4, "Lada", 1999, 700000));
        }};
        List<CarDTO> actualResult = carsDAO.findAll();

        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);
    }

    @Test
    void getById() {
        CarsDAO carsDAO = new CarsDAO(factory, loader);
        CarDTO exceptedResult = new CarDTO (1, "Maddyson", 1984,2000000);
        CarDTO actualResult = carsDAO.getById(1);
        Assertions.assertEquals(exceptedResult,actualResult);
    }

    @Test
    void deleteCarFromShop() {

    }

    @Test
    void carInParticularShop() {
    }
}