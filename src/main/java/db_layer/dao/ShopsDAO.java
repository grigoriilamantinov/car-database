package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarDTO;
import db_layer.dto.CarShopsDTO;
import db_layer.dto.ShopDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ShopsDAO implements DAO<ShopDTO>{

    private final ConnectionFactory connectionFactory;
    private final PropertiesLoader loader;

    public ShopsDAO(
        final ConnectionFactory connectionFactory,
        final PropertiesLoader loader
    ) {
        this.connectionFactory = connectionFactory;
        this.loader = loader;
    }

    private final static Logger logger = Logger.getLogger(CarsDAO.class.getName());
    @Override
    public ShopDTO getById(final int id) {
        final Connection connection = connectionFactory.connectionOpen();
        ResultSet resultSet = null;
        ShopDTO shopDTO = new ShopDTO();
        try {
            final PreparedStatement statement = connection.prepareStatement(
                String.format(loader.getStatementSelectShopById(),id)
            );
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                shopDTO = ShopDTO.of(resultSet);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            logger.info("Товарищ, что-то не так в запросе при обращении к таблице");
        } connectionFactory.closeConnection(connection);
        return shopDTO;
    }

    @Override
    public List<ShopDTO> findAll() {
        final Connection connection = connectionFactory.connectionOpen();
        final List<ShopDTO> result = new ArrayList<>();
        try {
            final PreparedStatement statement = connection.prepareStatement(loader.getStatementSelectAllShops());
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(ShopDTO.of(resultSet));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            logger.info("Товарищ, что-то не так в запросе при обращении к таблице");
        } finally {
            connectionFactory.closeConnection(connection);
        }
        return result;
    }

    public List<CarShopsDTO> allCarInOneShop(final int shopId) {
        final List<CarShopsDTO> carIntoShop = new ArrayList<>();
        final Connection connection = connectionFactory.connectionOpen();
        try {
            final PreparedStatement statement =
                connection.prepareStatement(String.format(loader.getStatementSelectCarJoinOneShop(), shopId));
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carIntoShop.add(
                    CarShopsDTO.builder()
                        .brand(resultSet.getString("brand"))
                        .shop(resultSet.getString("shop"))
                        .build()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("Товарищ, что-то не так в запросе при обращении к таблице");
        } connectionFactory.closeConnection(connection);
        return carIntoShop;
    }
}
