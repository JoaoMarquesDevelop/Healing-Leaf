package isle.academy.healing_leaf.data.dto.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class GroupOfItemsBaseDTO {
    private long gold;
    private long experience;
    private List<FeedstockDTO> feedstocks;
    private Set<CraftItemDTO> craftItems;

    public GroupOfItemsBaseDTO() {
        feedstocks = new ArrayList<>();
        craftItems = new HashSet<>();
    }
}
