package formatter;
import db_layer.dto.CarShopsDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarShopFormatter implements Formatter<CarShopsDTO> {
    public String carShopOnlyFromList(final List<CarShopsDTO> listDTO) {
        final StringJoiner joiner = new StringJoiner("\n");
        for (CarShopsDTO car : listDTO) {
            joiner.add(car.toStringCarShopOnly());
        }
        return joiner.toString();
    }
}
