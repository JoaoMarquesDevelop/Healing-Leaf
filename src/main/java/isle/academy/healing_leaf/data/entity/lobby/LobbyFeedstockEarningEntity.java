package isle.academy.healing_leaf.data.entity.lobby;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lobby_feedstock_earnings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LobbyFeedstockEarningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer identifier;

    @Column
    private Integer quantity;

    public void insert(int quantity) {
        this.quantity += quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lobby_steam_id", nullable = false)
    private LobbyEntity lobby;
}
