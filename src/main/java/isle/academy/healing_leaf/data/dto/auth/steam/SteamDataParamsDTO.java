package isle.academy.healing_leaf.data.dto.auth.steam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SteamDataParamsDTO {
    private String result;

    @JsonProperty(value = "steamid")
    private String steamId;

    @JsonProperty(value = "ownersteamid")
    private String ownerSteamId;

    @JsonProperty(value = "vacbanned")
    private boolean vacBanned;

    @JsonProperty(value = "publisherbanned")
    private boolean publisherBanned;
}
