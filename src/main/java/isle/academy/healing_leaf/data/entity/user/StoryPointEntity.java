package isle.academy.healing_leaf.data.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "story_points")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StoryPointEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "identifier")
    private int identifier;

    @Column(name = "created_date")
    private long createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryPointEntity that = (StoryPointEntity) o;
        return identifier == that.identifier && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, user);
    }
}
