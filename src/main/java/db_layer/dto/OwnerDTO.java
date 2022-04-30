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
    private int idCar;

    private final static String SPACE = " ";

    public static OwnerDTO of(final ResultSet resultSet) {
        OwnerDTO dto = new OwnerDTO();
        try {
            dto.setId(resultSet.getInt("id"));
            dto.setFirstName(resultSet.getString("first_name"));
            dto.setLastName(resultSet.getString("last_name"));
            dto.setIdCar(resultSet.getInt("car_id"));
        } catch (SQLException e) {
            System.out.println(String.format("Error on getting data from single row result set to create %s object", CarDTO.class));
        }
        return dto;
    }

    @Override
    public String toString() {
        return firstName + SPACE + lastName;
    }
}
