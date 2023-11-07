package isle.academy.healing_leaf.data.dto.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FeedstockDTO {
    private int id;
    private int quantity;
}
