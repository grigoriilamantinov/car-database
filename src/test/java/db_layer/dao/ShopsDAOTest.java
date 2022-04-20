package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarShopsDTO;
import db_layer.dto.OwnerDTO;
import db_layer.dto.ShopDTO;
import db_layer.propertiesLoader.PropertiesLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopsDAOTest {
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
    void getById() {
        ShopsDAO shopsDAO = new ShopsDAO(factory, loader);
        ShopDTO exceptedResult = new ShopDTO (1, "Кира Ауто");
        ShopDTO actualResult = shopsDAO.getById(1);
        Assertions.assertEquals(exceptedResult,actualResult);
    }

    @Test
    void findAll() {
        ShopsDAO shopsDAO = new ShopsDAO(factory, loader);

        final List<ShopDTO> exceptedResult = new ArrayList<>() {{
            add(new ShopDTO(1,"Кира Ауто"));
            add(new ShopDTO(2,"BNW"));
            add(new ShopDTO(3,"Е-мобилс"));
        }};

        List<ShopDTO> actualResult = shopsDAO.findAll();
        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
    }

    @Test
    void allCarInOneShop() {
        ShopsDAO shopsDAO = new ShopsDAO(factory, loader);
        List<CarShopsDTO> exceptedResult = new ArrayList<>() {{
           add(new CarShopsDTO (0,0,"Maddyson","BNW"));
           add(new CarShopsDTO (0,0,"Lada","BNW"));
           add(new CarShopsDTO (0,0,"Lada","BNW"));
        }};
        List<CarShopsDTO> actualResult = shopsDAO.allCarInOneShop(2);
        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);

    }
}