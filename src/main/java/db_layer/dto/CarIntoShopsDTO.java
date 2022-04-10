package db_layer.dto;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CarIntoShopsDTO {
    private int idShop;
    private int carId;

    private final static  String SPACE = " ";

    public static CarIntoShopsDTO of(ResultSet resultSet) {
        CarIntoShopsDTO dto = new CarIntoShopsDTO();
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
}
