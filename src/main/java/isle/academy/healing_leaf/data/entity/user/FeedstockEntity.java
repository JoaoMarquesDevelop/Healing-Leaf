package isle.academy.healing_leaf.data.entity.user;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Table(name = "feedstocks")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Slf4j
public class FeedstockEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "identifier")
    private Integer identifier;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public void insertQuantity(int value) {
        quantity += value;
    }

    public void removeQuantity(int value) {
        quantity -= value;
    }


    @Override
    public String toString() {
        return "FeedstockEntity{" +
                "feedstockIdentifier=" + identifier +
                ", quantity=" + quantity +
                ", user=" + user.getSteamId() +
                '}';
    }
}
