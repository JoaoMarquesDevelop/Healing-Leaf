package isle.academy.healing_leaf.data.entity.lobby;

import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.entity.user.UserEntity;
import isle.academy.healing_leaf.data.enums.LobbyState;
import isle.academy.healing_leaf.helpers.TimeHelper;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "lobbies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LobbyEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6861827993596655047L;

    @Id
    @Column(nullable = false, name = "lobby_steam_id")
    private long lobbySteamId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "lobby", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<UserInLobbyEntity> users;

    @Column
    private int difficulty;

    @Column
    private int stageId;

    @Column
    private long creationDate;

    @Column
    private long endDate;

    @Column(name = "experience_earning")
    private long experienceEarning;

    @Column(name = "gold_earning")
    private long goldEarning;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private LobbyState state;

    @OneToMany(mappedBy = "lobby", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<LobbyFeedstockEarningEntity> feedstocks;

    public void setEarnings(List<LobbyFeedstockEarningEntity> possibleFeedstockEarnings, int possibleGoldEarning, int possibleXpEarning) {
        this.feedstocks = possibleFeedstockEarnings;
        this.goldEarning = possibleGoldEarning;
        this.experienceEarning = possibleXpEarning;
    }

    public GroupOfItemsBaseDTO endLobby(boolean won, ModelMapper mapper) {
        state = won ? LobbyState.WON : LobbyState.LOST;

        endDate = TimeHelper.getCurrentTimeInIsleFormat();

        if (!won) {
            return new GroupOfItemsBaseDTO();
        }

        GroupOfItemsBaseDTO rewards = mapper.map(this, GroupOfItemsBaseDTO.class);
        owner.giveReward(rewards, mapper, false);

        for (UserInLobbyEntity user : users) {
            user.getUser().giveReward(rewards, mapper, false);
        }

        return rewards;
    }


    public void addNewUser(UserInLobbyEntity userInLobby) {
        users.add(userInLobby);
    }

    @Override
    public String toString() {
        return "{" +
                "lobbySteamId=" + lobbySteamId +
                ", owner=" + owner +
                ", users=" + users +
                ", difficulty=" + difficulty +
                ", stageId=" + stageId +
                '}';
    }
}
