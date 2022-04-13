package formatter;
import db_layer.dto.CarShopsDTO;
import java.util.List;
import java.util.StringJoiner;

public class ShopFormatter implements Formatter {
    public static String NEXT_LINE = "\n";

    public String allCarIntoShopFromList(List<CarShopsDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarShopsDTO car : listDTO) {
            joiner.add(car.toStringAllCarIntoShop());
        }
        return joiner.toString();
    }
}
