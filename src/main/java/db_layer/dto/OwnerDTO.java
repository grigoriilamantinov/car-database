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
public class OwnerDTO {
    private int id;
    private String firstName;
    private String lastName;


    public static OwnerDTO of(ResultSet resultSet) {
        OwnerDTO dto = new OwnerDTO();
        try {
            dto.setId(resultSet.getInt("id"));
            dto.setFirstName(resultSet.getString("first_name"));
            dto.setLastName(resultSet.getString("last_name"));
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так с этой строчкой");
        }
        return dto;
    }

    @Override
    public String toString() {
        return "id: "
            + id
            + ", "
            + firstName
            + " "
            + lastName;
    }


}
