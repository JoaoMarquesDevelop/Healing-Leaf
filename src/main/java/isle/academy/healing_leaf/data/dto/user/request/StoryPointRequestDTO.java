package isle.academy.healing_leaf.data.dto.user.request;

import lombok.Data;

import javax.validation.constraints.Min;

import static isle.academy.healing_leaf.data.StringsPackage.MUST_BE_AT_LEAST;

@Data
public class StoryPointRequestDTO {

    @Min(value = 1, message = MUST_BE_AT_LEAST + 1)
    private int id;
}
