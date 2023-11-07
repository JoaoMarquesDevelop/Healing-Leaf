package isle.academy.healing_leaf.data.dto.auth.steam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SteamErrorDTO {

    @JsonProperty(value = "errorcode")
    private String errorCode;

    @JsonProperty(value = "errordesc")
    private String errorDescription;
}
