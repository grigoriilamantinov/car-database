package db_layer.dao;
import java.util.List;

public interface DAO<DTO> {

    DTO getById(int id);

    List<DTO> findAll();

    String DAO_MESSAGE = "Error %s trying execute SQL query: %s";
}
