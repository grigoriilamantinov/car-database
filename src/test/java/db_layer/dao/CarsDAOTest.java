package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarShopsDTO;
import db_layer.propertiesLoader.PropertiesLoader;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;

import javax.swing.text.TabableView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarsDAOTest {

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

    @AfterEach
    void clearData() {
        tableCreator.dropAllTables();
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
        CarsDAO carsDAO = new CarsDAO(factory, loader);
        CarShopsDAO carShopsDAO = new CarShopsDAO(factory, loader);

        final List<CarShopsDTO> exceptedResult = new ArrayList<>() {{
            add(new CarShopsDTO(3, 1));
            add(new CarShopsDTO(1, 2));
            add(new CarShopsDTO(2, 2));
            add(new CarShopsDTO(1, 3));
            add(new CarShopsDTO(1, 4));
            add(new CarShopsDTO(2, 4));
            add(new CarShopsDTO(3, 4));
        }};

        carsDAO.deleteCarFromShop(1, 2);
        List<CarShopsDTO> actualResult = carShopsDAO.findAll();

        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);
    }

    @Test
    void shouldGetCarInShops() {
        CarsDAO carsDAO = new CarsDAO(factory, loader);
        final List<CarShopsDTO> exceptedResult = new ArrayList<>() {{
            add(new CarShopsDTO(0, 0, "Maddyson", "BNW"));
            add(new CarShopsDTO(0, 0, "Maddyson", "Е-мобилс"));
        }};
        List<CarShopsDTO> actualResult = carsDAO.carInParticularShop(1);
        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);
    }
}