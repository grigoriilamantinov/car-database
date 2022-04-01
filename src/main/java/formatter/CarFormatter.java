package formatter;

import dto.CarDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarFormatter implements Formatter {
    public static String NEXT_LINE = "\n";

    @Override
    public String formatFromList(List<CarDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarDTO car : listDTO) {
            joiner.add(car.toString());
        }
        return joiner.toString();
    }

}
