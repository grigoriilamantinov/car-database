package formatter;
import db_layer.dto.CarShopsDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarShopFormatter implements Formatter<CarShopsDTO> {
    public static String NEXT_LINE = "\n";

    public String carShopOnlyFromList(List<CarShopsDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarShopsDTO car : listDTO) {
            joiner.add(car.toStringCarShopOnly());
        }
        return joiner.toString();
    }
}
