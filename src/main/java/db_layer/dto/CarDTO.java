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

    private Integer id;
    private String brand;
    private Integer year;
    private Integer cost;
    private Integer owner_id;
    private OwnerDTO owner;

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

    public CarDTO id (int id) {
        this.id = id;
        return this;
    }

    public CarDTO brand (String brand) {
        this.brand = brand;
        return this;
    }

    public CarDTO owner (OwnerDTO owner) {
        this.owner = owner;
        return this;
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
            + ". ID владельца: "
            + owner_id;
    }

    public String toStringOwnersCar() {
        return "id: "
            + id
            + ", брэнд "
            + brand
            + ". Владелец: "
            + owner;
    }
}
