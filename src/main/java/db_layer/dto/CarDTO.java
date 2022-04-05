package db_layer.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
@EqualsAndHashCode
public class CarDTO {

    private int id;
    private String brand;
    private int year;
    private int cost;

    public CarDTO(int id,
                  String brand,
                  int year,
                  int cost) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.cost = cost;
    }

    public CarDTO() {
    }

    public static CarDTO of(ResultSet resultSet){
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
}
