import db_layer.connection.ConnectionFactory;
import db_layer.dao.CarShopsDAO;
import db_layer.dao.CarsDAO;
import db_layer.dao.OwnersDAO;
import db_layer.dao.ShopsDAO;
import db_layer.propertiesLoader.PropertiesLoader;
import lombok.Getter;
import service_layer.CarService;

public class DAOFacade {
    @Getter private final CarsDAO carsDAO;
    @Getter private final OwnersDAO ownersDAO;
    @Getter private final ShopsDAO shopsDAO;
    @Getter private final CarShopsDAO carIntoShopsDAO;
    @Getter private final CarService carService;

    public DAOFacade (
        final ConnectionFactory connectionFactory,
        final PropertiesLoader loader
    ) {
        carsDAO = new CarsDAO(connectionFactory, loader);
        ownersDAO = new OwnersDAO(connectionFactory, loader);
        shopsDAO = new ShopsDAO(connectionFactory, loader);
        carIntoShopsDAO = new CarShopsDAO(connectionFactory, loader);
        carService = new CarService(carsDAO);
    }
}
