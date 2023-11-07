package isle.academy.healing_leaf.data.entity.user;

import isle.academy.healing_leaf.data.enums.CraftItemType;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "craft_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CraftItemEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CraftItemType type;

    @Column(name = "identifier")
    private int identifier;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "acquiredDate")
    private long acquiredDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftItemEntity that = (CraftItemEntity) o;
        return type == that.type &&
                identifier == that.identifier &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, user, type);
    }
}
