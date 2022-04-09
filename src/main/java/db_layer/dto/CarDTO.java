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
    private OwnerDTO owner;

    public CarDTO(Integer id, String brand, Integer year, Integer cost) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.cost = cost;
    }

    public static CarDTO of(ResultSet resultSet) {
        CarDTO dto = new CarDTO();
        try {
            dto.setId(resultSet.getInt("id"));
            dto.setBrand(resultSet.getString("brand"));
            dto.setYear(resultSet.getInt("year_of_produce"));
            dto.setCost(resultSet.getInt("net_worth"));
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
            + cost;
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
