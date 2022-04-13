import db_layer.connection.ConnectionFactory;
import db_layer.dao.CarShopsDAO;
import db_layer.dao.CarsDAO;
import db_layer.dao.OwnersDAO;
import db_layer.dao.ShopsDAO;
import lombok.Getter;
import service_layer.CarService;

public class DAOFacade {
    @Getter private final CarsDAO carsDAO;
    @Getter private final OwnersDAO ownersDAO;
    @Getter private final ShopsDAO shopsDAO;
    @Getter private final CarShopsDAO carIntoShopsDAO;
    @Getter private final CarService carService;

    public DAOFacade (ConnectionFactory connectionFactory, String dataSource) {
        carsDAO = new CarsDAO(connectionFactory, dataSource);
        ownersDAO = new OwnersDAO(connectionFactory, dataSource);
        shopsDAO = new ShopsDAO(connectionFactory, dataSource);
        carIntoShopsDAO = new CarShopsDAO(connectionFactory, dataSource);
        carService = new CarService(carsDAO);
    }
}
