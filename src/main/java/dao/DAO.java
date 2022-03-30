package dao;


import dto.CarsDTO;

import java.util.List;

public interface DAO {

    void createTable();

    List<CarsDTO> findAll();

    CarsDTO getById(int id);

    void addString(CarsDTO usersObject);

    void deleteById(int id);

    void dropTable();

    void update(CarsDTO dataForUpdate);
}
