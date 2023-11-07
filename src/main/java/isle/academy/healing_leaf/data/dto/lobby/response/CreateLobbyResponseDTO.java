package isle.academy.healing_leaf.data.dto.lobby.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLobbyResponseDTO {
    private List<WaveRewardsResponseDTO> waves;
}
