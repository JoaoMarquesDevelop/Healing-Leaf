package isle.academy.healing_leaf.data.entity.lobby;

import isle.academy.healing_leaf.data.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "users_in_lobby")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInLobbyEntity implements Serializable {

    private static final long serialVersionUID = 8471452584250977495L;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lobby_steam_id", nullable = false)
    private LobbyEntity lobby;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInLobbyEntity that = (UserInLobbyEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
