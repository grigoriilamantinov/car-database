package formatter;

import dto.CarsDTO;

import java.util.List;
import java.util.StringJoiner;

public class CarFormatter implements Formatter {
    public static String NEXT_LINE = "\n";

    @Override
    public String formatFromList(List<CarsDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
        for (CarsDTO car : listDTO) {
            joiner.add(car.toString());
        }
        return joiner.toString();
    }

}
