package formatter;
import db_layer.dto.CarShopsDTO;
import db_layer.dto.ShopDTO;

import java.util.List;
import java.util.StringJoiner;

public class ShopFormatter implements Formatter<ShopDTO> {
    public String allCarIntoShopFromList(final List<CarShopsDTO> listDTO) {
        final StringJoiner joiner = new StringJoiner("\n");
        for (CarShopsDTO car : listDTO) {
            joiner.add(car.toStringAllCarIntoShop());
        }
        return joiner.toString();
    }
}
