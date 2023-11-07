package isle.academy.healing_leaf.helpers;

import isle.academy.healing_leaf.data.dto.store.PriceResponseDTO;

import java.util.HashMap;
import java.util.Map;

public class Converters {

    public static Map<Integer, PriceResponseDTO> convertPriceResponseArrayToHashmap(PriceResponseDTO[] array) {

        Map<Integer, PriceResponseDTO> map = new HashMap<>();

        for (PriceResponseDTO price : array) {
            map.put(price.getId(), price);
        }

        return map;
    }
}
