package isle.academy.healing_leaf.data.dto.lobby.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WaveRewardsResponseDTO {
    private List<CreatureRewardsResponseDTO> rewards = new ArrayList<>();
}
