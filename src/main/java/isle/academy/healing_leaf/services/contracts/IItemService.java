package isle.academy.healing_leaf.services.contracts;

import isle.academy.healing_leaf.data.dto.items.CraftItemDTO;
import isle.academy.healing_leaf.data.dto.store.StoreDataResponseDTO;

public interface IItemService {

    StoreDataResponseDTO getStore();

    void craftItem(long steamId, CraftItemDTO craftItemRequestDTO);

}
