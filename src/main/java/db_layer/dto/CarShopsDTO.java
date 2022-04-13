package db_layer.dto;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarShopsDTO {
    private int idShop;
    private int carId;
    private String brand;
    private String shop;
    private CarDTO carDTO;
    private ShopDTO shopDTO;

    private final static String SPACE = " ";

    public static CarShopsDTO of(final ResultSet resultSet) {
        final CarShopsDTO dto = new CarShopsDTO();
        try {
            dto.setCarId(resultSet.getInt("car_id"));
            dto.setIdShop(resultSet.getInt("id_shop"));
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так с этой строчкой");
        }
        return dto;
    }

    @Override
    public String toString() {
        return "Автомобиль ID: "
            + carId
            + SPACE
            + "Магазин ID: "
            + idShop;
    }

    public String toStringCarShopOnly() {
        return "Автомобиль: "
            + brand
            + SPACE
            + "Магазин: "
            + shop;
    }

    public  String toStringAllCarIntoShop() {
        return "Магазин: "
            + shop
            + ". "
            + "Автомобиль: "
            + brand;
    }
}
