package formatter;

import db_layer.dto.CarDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarFormatter implements Formatter<CarDTO> {
    public String ownersCarFromList(final List<CarDTO> listDTO) {
        final StringJoiner joiner = new StringJoiner("\n");
        for (CarDTO car : listDTO) {
            joiner.add(car.toStringOwnersCar());
        }
        return joiner.toString();
    }
}
