package db_layer.dao;


import db_layer.dto.CarDTO;

import java.util.List;

public interface DAO {

    void createTable();

    CarDTO getById(int id);

    void save(CarDTO usersObject);

    void deleteById(int id);

    void dropTable();

    List<CarDTO> findAll();

    void update(CarDTO dataForUpdate);

    class DTO {
    }
}
