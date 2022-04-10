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
public class ShopDTO {
    private int shopId;
    private String shopName;

    public static ShopDTO of(ResultSet resultSet) {
        ShopDTO dto = new ShopDTO();
        try {
            dto.setShopId(resultSet.getInt("shop_id"));
            dto.setShopName(resultSet.getString("shop"));
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так с этой строчкой");
        }
        return dto;
    }

    @Override
    public String toString() {
        return "ShopDTO{" +
            "shopId=" + shopId +
            ", shopName='" + shopName + '\'' +
            '}';
    }
}
