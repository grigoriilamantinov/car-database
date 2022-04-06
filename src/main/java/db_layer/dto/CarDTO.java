package db_layer.dto;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private int id;
    private String brand;
    private int year;
    private int cost;
    private int owner_id;

    public static CarDTO of(ResultSet resultSet) {
        CarDTO dto = new CarDTO();
        try {
            dto.setId(resultSet.getInt("id"));
            dto.setBrand(resultSet.getString("brand"));
            dto.setYear(resultSet.getInt("year_of_produce"));
            dto.setCost(resultSet.getInt("net_worth"));
            dto.setOwner_id(resultSet.getInt("owner_id"));
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так с этой строчкой");
        }
        return dto;
    }

    @Override
    public String toString() {
        return "id: "
                + id
                + ", брэнд "
                + brand
                + ", год: "
                + year
                + ", цена: "
                + cost
                + ", владелец: "
                + owner_id;
    }
}
