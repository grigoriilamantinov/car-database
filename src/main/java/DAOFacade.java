import db_layer.connection.ConnectionFactory;
import db_layer.dao.CarShopsDAO;
import db_layer.dao.CarsDAO;
import db_layer.dao.OwnersDAO;
import db_layer.dao.ShopsDAO;
import lombok.Getter;
import service_layer.CarService;

public class DAOFacade {

    private ConnectionFactory connectionFactory;
    private String datasource;

    @Getter private CarsDAO carsDAO;
    @Getter private OwnersDAO ownersDAO;
    @Getter private ShopsDAO shopsDAO;
    @Getter private CarShopsDAO carIntoShopsDAO;
    @Getter private CarService carService;

    public DAOFacade (ConnectionFactory connectionFactory, String dataSource) {
        this.connectionFactory = connectionFactory;
        this.datasource = dataSource;
        carsDAO = new CarsDAO(connectionFactory, dataSource);
        ownersDAO = new OwnersDAO(connectionFactory, dataSource);
        shopsDAO = new ShopsDAO(connectionFactory, dataSource);
        carIntoShopsDAO = new CarShopsDAO(connectionFactory, dataSource);
        carService = new CarService(carsDAO);
    }
}
