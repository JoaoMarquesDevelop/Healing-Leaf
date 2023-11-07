package isle.academy.healing_leaf.data.dto.auth.steam;

import lombok.Data;

@Data
public class SteamResponseDTO {

    private SteamDataParamsDTO params;
    private SteamErrorDTO error;
}
