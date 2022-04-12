package db_layer.propertiesLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    private final String dataSource;

    public PropertiesLoader(String dataSource) {
        this.dataSource = dataSource;
        this.loadDataSourceProperties();
    }

    private static final String CREATE_STATE_ALL_TABLES = "createTables";
    private static final String MENU = "menu";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String DB_URL = "db_url";
    private static final String DRIVER = "driverName";
    private static final String SELECT_CAR_SHOPS_ID  = "selectCarShopId";
    private static final String SELECT_ALL_CARS = "selectAllCar";
    private static final String SELECT_CAR_BY_ID = "carById";
    private static final String DEL_CAR_BY_ID = "deleteCarById";
    private static final String SELECT_CAR_SHOPS  = "selectCarShop";
    private static final String DEL_FROM_CAR_SHOP_BY_ID = "deleteByTwoId";
    private static final String SELECT_ALL_OWNERS = "selectAllOwners";
    private static final String SELECT_CAR_JOIN_OWNER = "selectCarJoinOwner";
    private static final String SELECT_OWNER_BY_ID = "ownerById";
    private static final String SELECT_ALL_SHOPS = "selectAllShops";
    private static final String SELECT_CAR_JOIN_ONE_SHOP = "selectCarJoinOneShop";

    private final Properties properties = new Properties();

    private void loadDataSourceProperties() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(dataSource);
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCreateStateAllTables() {
        return properties.getProperty(CREATE_STATE_ALL_TABLES);
    }

    public String getMenu() {
        return properties.getProperty(MENU);
    }

    public String getStatementDeleteCarById() {
        return properties.getProperty(DEL_CAR_BY_ID);
    }

    public String getStatementSelectCarShop() {
        return properties.getProperty(SELECT_CAR_SHOPS);
    }

    public String getStatementSelectAllShops() {
        return properties.getProperty(SELECT_ALL_SHOPS);
    }

    public String getStatementSelectCarJoinOneShop() {
        return properties.getProperty(SELECT_CAR_JOIN_ONE_SHOP);
    }

    public String getStatementSelectCarShopById() {
        return properties.getProperty(SELECT_CAR_SHOPS_ID);
    }

    public String getStatementSelectOwnerById() {
        return properties.getProperty(SELECT_OWNER_BY_ID);
    }

    public String getStatementDelFromCarShop() {
        return properties.getProperty(DEL_FROM_CAR_SHOP_BY_ID);
    }

    public String getStatementSelectCarOnOwner() {
        return properties.getProperty(SELECT_CAR_JOIN_OWNER);
    }



    public String getStatementSelectCarById() {
        return properties.getProperty(SELECT_CAR_BY_ID);
    }
    public String getUser() {
        return properties.getProperty(USER);
    }

    public String getStatementSelectAllOwners() {
        return properties.getProperty(SELECT_ALL_OWNERS);
    }

    public String getPassword() {
        return properties.getProperty(PASSWORD);
    }

    public String getStatementSelectCars() {
        return properties.getProperty(SELECT_ALL_CARS);
    }

    public String getDbUrl() {
        return properties.getProperty(DB_URL);
    }

    public String getDriver() {
        return properties.getProperty(DRIVER);
    }
}
