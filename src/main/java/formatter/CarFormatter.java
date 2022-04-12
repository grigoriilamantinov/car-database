package formatter;

import db_layer.dto.CarDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarFormatter implements Formatter<CarDTO> {
    public static String NEXT_LINE = "\n";

    public String ownersCarFromList(List<CarDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarDTO car : listDTO) {
            joiner.add(car.toStringOwnersCar());
        }
        return joiner.toString();
    }
}
