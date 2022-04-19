package db_layer.dao;

import db_layer.connection.ConnectionFactory;
import db_layer.dto.CarShopsDTO;
import db_layer.dto.ShopDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ShopDTO getById(final int id) {
        final Connection connection = connectionFactory.connectionOpen();
        ResultSet resultSet = null;
        try {
            final PreparedStatement statement = connection.prepareStatement(
                String.format(loader.getStatementSelectShopById(),id)
            );
            resultSet = statement.executeQuery();
            resultSet.next();
        } catch (final SQLException e) {
            e.printStackTrace();
        } connectionFactory.closeConnection(connection);
        return ShopDTO.of(resultSet);
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
        } connectionFactory.closeConnection(connection);
        return carIntoShop;
    }
}
