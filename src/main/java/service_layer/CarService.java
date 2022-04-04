package service_layer;

import db_layer.dao.CarsDAO;
import db_layer.dto.CarDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CarService {
    private CarsDAO cars;

    public CarService(CarsDAO cars) {
        this.cars = cars;
    }

    public List<CarDTO> getCarsBetweenYears (int year1, int year2) {
        return cars.findAll().stream()
            .filter(cars -> cars.getYear() < year2 && cars.getYear() > year1)
            .collect(Collectors.toList());
    }

    public List<CarDTO> getCarsLessCost (int cost) {
        return cars.findAll().stream()
            .filter(cars -> cars.getCost() < cost)
            .collect(Collectors.toList());
    }

}
