package formatter;

import dto.CarsDTO;

import java.util.List;

public interface Formatter {
    String formatFromList(List<CarsDTO> listDTO);
}
