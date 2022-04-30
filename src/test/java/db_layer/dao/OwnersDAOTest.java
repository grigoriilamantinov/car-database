package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.OwnerDTO;
import db_layer.propertiesLoader.PropertiesLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OwnersDAOTest {
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
    void shouldFindAllOwners() {
        OwnersDAO ownersDAO = new OwnersDAO(factory, loader);

        final List<OwnerDTO> exceptedResult = new ArrayList<>() {{
            add(new OwnerDTO(1,"Михаил","Шишкин", 1));
            add(new OwnerDTO(2,"Михаэль","Шумахер", 2));
            add(new OwnerDTO(3,"Капибар","Григорьевич", 13));
            add(new OwnerDTO(4,"Али","Дон-Донович", 20));
            add(new OwnerDTO(5,"Имануил","Кант", 11));
            add(new OwnerDTO(6,"Иван","Иванов", 7));
        }};

        List<OwnerDTO> actualResult = ownersDAO.findAll();
        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);
    }

    @Test
    void shouldGetOwnerById() {
        OwnersDAO ownersDAO = new OwnersDAO(factory, loader);
        OwnerDTO exceptedResult = new OwnerDTO (1, "Михаил", "Шишкин",1);
        OwnerDTO actualResult = ownersDAO.getById(1);
        Assertions.assertEquals(exceptedResult,actualResult);
    }
}