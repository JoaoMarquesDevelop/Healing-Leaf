package isle.academy.healing_leaf.data.dto.store;

import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import lombok.Data;

import java.util.List;

@Data
public class PriceResponseDTO {

    private int id;
    private String name;
    private List<FeedstockDTO> feedstocks;
    private long gold;

    @Override
    public String toString() {
        return name;
    }
}
