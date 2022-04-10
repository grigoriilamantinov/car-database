package formatter;

import db_layer.dto.OwnerDTO;
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
}
