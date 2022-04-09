package db_layer.dao;


import db_layer.dto.CarDTO;

import java.util.List;

public interface DAO<DTO> {

    DTO getById(int id);

    void save(DTO usersObject);

    void deleteById(int id);

    List<DTO> findAll();

    void update(DTO dataForUpdate);

}
