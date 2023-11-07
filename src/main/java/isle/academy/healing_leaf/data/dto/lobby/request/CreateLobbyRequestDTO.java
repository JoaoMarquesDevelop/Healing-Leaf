package isle.academy.healing_leaf.data.dto.lobby.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static isle.academy.healing_leaf.data.StringsPackage.MUST_BE_AT_LEAST;
import static isle.academy.healing_leaf.data.StringsPackage.MUST_BE_AT_MAX;

@Data
public class CreateLobbyRequestDTO {

    @Min(value = 1, message = MUST_BE_AT_LEAST + 1)
    private long lobbySteamId;

    @Min(value = 1, message = MUST_BE_AT_LEAST + 1)
    private int stageId;

    @Min(value = 1, message = MUST_BE_AT_LEAST + 1)
    @Max(value = 3, message = MUST_BE_AT_MAX + 3)
    private int difficulty;

    private long[] otherUsersInLobby;
}
