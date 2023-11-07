package isle.academy.healing_leaf.data.dto.items;

import isle.academy.healing_leaf.data.enums.CraftItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.Objects;

import static isle.academy.healing_leaf.data.StringsPackage.MUST_BE_AT_LEAST;

@Data
@AllArgsConstructor
@Builder
public class CraftItemDTO {

    @Min(value = 1, message = MUST_BE_AT_LEAST + 1)
    private int id;

    private CraftItemType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftItemDTO that = (CraftItemDTO) o;
        return id == that.id && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
