package isle.academy.healing_leaf.data.dto.lobby.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatureRewardsResponseDTO {
    private int creatureId;
    private List<Integer> drops;
    private long gold;
    private long xp;
}
