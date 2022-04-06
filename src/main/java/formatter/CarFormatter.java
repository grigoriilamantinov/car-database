package formatter;

import db_layer.dto.CarDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarFormatter {
    public static String NEXT_LINE = "\n";

    public String carFromList(List<CarDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarDTO car : listDTO) {
            joiner.add(car.toString());
        }
        return joiner.toString();
    }

}
