package isle.academy.healing_leaf.data.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StoreDataResponseDTO {

    private PriceResponseDTO[] towers;
    private PriceResponseDTO[] blocks;
    private PriceResponseDTO[] traps;
}
