package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
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
            e.printStackTrace();
        }
        return dto;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO that = (CarDTO) o;
        return id == that.id && year == that.year && cost == that.cost && brand.equals(that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, year, cost);
    }

}
