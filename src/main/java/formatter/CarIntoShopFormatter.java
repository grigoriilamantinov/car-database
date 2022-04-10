package formatter;
import db_layer.dto.CarIntoShopsDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarIntoShopFormatter {
    public static String NEXT_LINE = "\n";

    public String carIntoShopFromList(List<CarIntoShopsDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarIntoShopsDTO car : listDTO) {
            joiner.add(car.toString());
        }
        return joiner.toString();
    }

    public String carShopOnlyFromList(List<CarIntoShopsDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarIntoShopsDTO car : listDTO) {
            joiner.add(car.toStringCarShopOnly());
        }
        return joiner.toString();
    }
}
