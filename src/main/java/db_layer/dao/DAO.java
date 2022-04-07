package db_layer.dao;


import db_layer.dto.CarDTO;

import java.util.List;

public interface DAO<DTO> {

    void createTable();

    DTO getById(int id);

    void save(DTO usersObject);

    void deleteById(int id);

    void dropTable();

    List<DTO> findAll();

    void update(DTO dataForUpdate);

}
