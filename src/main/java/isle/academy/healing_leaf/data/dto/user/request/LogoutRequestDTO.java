package isle.academy.healing_leaf.data.dto.user.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static isle.academy.healing_leaf.data.StringsPackage.MUST_BE_AT_LEAST;
import static isle.academy.healing_leaf.data.StringsPackage.MUST_BE_AT_MAX;


@Data
public class LogoutRequestDTO {

    private float positionX;
    private float positionY;
    private float positionZ;

    @Min(value = 0, message = MUST_BE_AT_LEAST + 0)
    @Max(value = 10, message = MUST_BE_AT_MAX + 10)
    private int soundtrackVolume;

    @Min(value = 0, message = MUST_BE_AT_LEAST + 0)
    @Max(value = 10, message = MUST_BE_AT_MAX + 10)
    private int uiVolume;

    @Min(value = 0, message = MUST_BE_AT_LEAST + 0)
    @Max(value = 10, message = MUST_BE_AT_MAX + 10)
    private int fightVolume;
}
