package service_layer;

import db_layer.dao.CarsDAO;
import db_layer.dto.CarDTO;
import db_layer.dto.YearDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class CarServiceTest {

    final CarsDAO cars = Mockito.mock(CarsDAO.class);
    final CarService carService = new CarService(cars);

    @Test
    void shouldGetCarsBetweenYears() {

        final List<CarDTO> preparedData = new ArrayList<>() {{
            add(new CarDTO(1, "Lada", 1940, 999999));
            add(new CarDTO(2, "Maddyson", 1999, 1999999));
            add(new CarDTO(3, "GAZ", 2100, 2999999));
            add(new CarDTO(4, "Lada", 2014, 399999));
            add(new CarDTO(5, "Cam", 1940, 499999));
            add(new CarDTO(6, "Moscow", 2003, 1999999));
            add(new CarDTO(7, "4x4", 2019, 4999999));
        }};
        final YearDTO yearDTO = new YearDTO(1990, 2004);

        final List<CarDTO> exceptedResult = new ArrayList<>() {{
            add(new CarDTO(2, "Maddyson", 1999, 1999999));
            add(new CarDTO(6, "Moscow", 2003, 1999999));
        }};

        Mockito.when(cars.findAll()).thenReturn(preparedData);
        final List<CarDTO> actualResult = carService.getCarsBetweenYears(yearDTO.getYearFrom(), yearDTO.getYearTo());

        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);
    }

    @Test
    void shouldGetCarsLessCost() {

        final List<CarDTO> preparedData = new ArrayList<>() {{
            add(new CarDTO(1, "Lada", 1940, 999999));
            add(new CarDTO(2, "Maddyson", 1999, 1999999));
            add(new CarDTO(3, "GAZ", 2100, 2999999));
            add(new CarDTO(4, "Lada", 2014, 399999));
            add(new CarDTO(5, "Cam", 1940, 499999));
            add(new CarDTO(6, "Moscow", 2003, 1999999));
            add(new CarDTO(7, "4x4", 2019, 4999999));
        }};

        final List<CarDTO> exceptedResult = new ArrayList<>() {{
            add(new CarDTO(1, "Lada", 1940, 999999));
            add(new CarDTO(4, "Lada", 2014, 399999));
            add(new CarDTO(5, "Cam", 1940, 499999));
        }};

        Mockito.when(cars.findAll()).thenReturn(preparedData);
        final List<CarDTO> actualResult = carService.getCarsCostLessThan(1000000);

        Assertions.assertEquals(exceptedResult.size(), actualResult.size());
        Assertions.assertTrue(actualResult.containsAll(exceptedResult));
        Assertions.assertIterableEquals(exceptedResult, actualResult);
    }
}