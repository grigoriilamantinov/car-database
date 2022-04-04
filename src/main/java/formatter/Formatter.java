package formatter;

import db_layer.dto.CarDTO;

import java.util.List;

public interface Formatter {
    String formatFromList(List<CarDTO> listDTO);
}
