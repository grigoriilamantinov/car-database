package dto;

import java.util.Objects;
public class CarsDTO {
    int id;
    String brand;
    int year;
    int cost;

    public CarsDTO(int id, String brand, int year, int cost) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.cost = cost;
    }

    public CarsDTO() {
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
        return "Строчка содержит - id: "
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
        CarsDTO that = (CarsDTO) o;
        return id == that.id && year == that.year && cost == that.cost && brand.equals(that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, year, cost);
    }
}
