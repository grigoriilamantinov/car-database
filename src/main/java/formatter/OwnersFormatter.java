package formatter;

import db_layer.dto.OwnerDTO;

import java.util.List;
import java.util.StringJoiner;

public class OwnersFormatter {
    public static String NEXT_LINE = "\n";

    public String ownersFromList(List<OwnerDTO> listDTO) {
        StringJoiner joiner = new StringJoiner(NEXT_LINE);
            for (OwnerDTO owner : listDTO) {
                joiner.add(owner.toString());
            }
        return joiner.toString();
    }
}
