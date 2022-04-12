package formatter;

import java.util.List;
import java.util.StringJoiner;

public interface Formatter<DTO> {
    default String getFromList(List<DTO> listDTO) {
        StringJoiner joiner = new StringJoiner("\n");
        for (DTO dto : listDTO) {
            joiner.add(dto.toString());
        }
        return joiner.toString();
    }
}
