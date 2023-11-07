package isle.academy.healing_leaf.data.dto.user;

import isle.academy.healing_leaf.data.dto.items.CraftItemDTO;
import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private long steamId;
    private long gold;
    private long experience;
    private float positionX;
    private float positionY;
    private float positionZ;
    private int storyIndex;
    private int soundtrackVolume;
    private int uiVolume;
    private int fightVolume;
    private int currentIsland;
    private List<FeedstockDTO> feedstocks;
    private Set<CraftItemDTO> craftItems;
    private List<Integer> storyPoints;
    private List<FeedstockDTO> bonusFeedstocks;
}
