package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarShopsDTO;
import db_layer.propertiesLoader.PropertiesLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CarShopsDAOTest {
    PropertiesLoader loader = new PropertiesLoader(
        "src/test/resources/test.properties"
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
    void shouldFindAllRows() {
        CarShopsDAO carShopsDAO = new CarShopsDAO(factory, loader);

        final List<CarShopsDTO> exceptedResult = new ArrayList<>() {{
            add(new CarShopsDTO(2, 1));
            add(new CarShopsDTO(3, 1));
            add(new CarShopsDTO(1, 2));
            add(new CarShopsDTO(2, 2));
            add(new CarShopsDTO(1, 3));
            add(new CarShopsDTO(1, 4));
            add(new CarShopsDTO(2, 4));
            add(new CarShopsDTO(3, 4));
        }};

        List<CarShopsDTO> actualResult = carShopsDAO.findAll();
        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);
    }

}