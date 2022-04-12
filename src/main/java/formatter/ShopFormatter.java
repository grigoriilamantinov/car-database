package formatter;

import db_layer.dto.CarShopsDTO;
import db_layer.dto.ShopDTO;

import java.util.List;
import java.util.StringJoiner;

public class ShopFormatter {
    public static String NEXT_LINE = "\n";

    public String shopsFromList(List<ShopDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (ShopDTO shop : listDTO) {
            joiner.add(shop.toString());
        }
        return joiner.toString();
    }

    public String allCarIntoShopFromList(List<CarShopsDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarShopsDTO car : listDTO) {
            joiner.add(car.toStringAllCarIntoShop());
        }
        return joiner.toString();
    }
}
