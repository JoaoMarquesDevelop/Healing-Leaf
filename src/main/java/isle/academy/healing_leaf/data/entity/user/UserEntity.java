package isle.academy.healing_leaf.data.entity.user;

import isle.academy.healing_leaf.data.dto.items.CraftItemDTO;
import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.user.request.LogoutRequestDTO;
import isle.academy.healing_leaf.helpers.TimeHelper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Slf4j
@Builder
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id")
    private long steamId;

    @Column(name = "experience")
    private long experience;

    @Setter
    @Column(name = "position_x")
    private float positionX;

    @Setter
    @Column(name = "position_y")
    private float positionY;

    @Setter
    @Column(name = "position_z")
    private float positionZ;

    @Column(name = "story_index")
    private int storyIndex;

    @Column(name = "gold")
    private long gold;

    @Column(name = "created_date")
    private long createdDate;

    @Setter
    @Column(name = "last_active")
    private long lastActive;

    @Setter
    @Column(name = "soundtrack_volume")
    private int soundtrackVolume;

    @Setter
    @Column(name = "ui_volume")
    private int uiVolume;

    @Setter
    @Column(name = "fight_volume")
    private int fightVolume;

    @Setter
    @Column(name = "current_island")
    private int currentIsland;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    private List<FeedstockEntity> feedstocks = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    private Set<CraftItemEntity> craftItems = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    private Set<StoryPointEntity> storyPoints = new HashSet<>();

    /**
     * @return true if the user didn't have the story point before
     */
    public boolean insertNewStoryPoint(int storyPointId) {
        StoryPointEntity newStoryPoint = StoryPointEntity.builder()
                .user(this)
                .createdDate(TimeHelper.getCurrentTimeInIsleFormat())
                .identifier(storyPointId)
                .build();

        return storyPoints.add(newStoryPoint);
    }

    public void setLogout(LogoutRequestDTO logoutRequestDTO) {

        setPositionX(logoutRequestDTO.getPositionX());
        setPositionY(logoutRequestDTO.getPositionY());
        setPositionZ(logoutRequestDTO.getPositionZ());
        setSoundtrackVolume(logoutRequestDTO.getSoundtrackVolume());
        setUiVolume(logoutRequestDTO.getUiVolume());
        setFightVolume(logoutRequestDTO.getFightVolume());
        setLastActive(TimeHelper.getCurrentTimeInIsleFormat());
    }

    /**
     * @return the storyIndex after the change
     */
    public int nextStory() {
        storyIndex++;
        return storyIndex;
    }

    public boolean hasEnoughFeedstocks(List<FeedstockDTO> extraFeedstocks) {
        return extraFeedstocks
                .stream()
                .allMatch(this::hasEnoughFeedstocks);
    }

    private boolean hasEnoughFeedstocks(FeedstockDTO extraFeedstock) {

        Optional<FeedstockEntity> userFeedstock = feedstocks
                .stream()
                .filter(x -> x.getIdentifier() == extraFeedstock.getId())
                .findFirst();

        return userFeedstock
                .filter(feedstockEntity -> feedstockEntity.getQuantity() - extraFeedstock.getQuantity() >= 0)
                .isPresent();
    }

    public void giveReward(GroupOfItemsBaseDTO groupOfItemsBaseDTO, ModelMapper mapper, boolean payment) {

        if (groupOfItemsBaseDTO.getFeedstocks() == null) {
            groupOfItemsBaseDTO.setFeedstocks(new ArrayList<>());
        }

        if (groupOfItemsBaseDTO.getCraftItems() == null) {
            groupOfItemsBaseDTO.setCraftItems(new HashSet<>());
        }

        boolean hasFeedstock;

        for (FeedstockDTO newFeedstock : groupOfItemsBaseDTO.getFeedstocks()) {

            hasFeedstock = ifHasFeedstockGiveIt(newFeedstock, payment);

            if (!hasFeedstock && !payment) {
                FeedstockEntity feedstockEntity = mapper.map(newFeedstock, FeedstockEntity.class);
                feedstockEntity.setUser(this);
                this.feedstocks.add(feedstockEntity);
            }
        }

        for (CraftItemDTO craftItemDTO : groupOfItemsBaseDTO.getCraftItems()) {
            CraftItemEntity craftItem = mapper.map(craftItemDTO, CraftItemEntity.class);
            craftItem.setUser(this);
            craftItems.add(craftItem);
        }

        if (payment) {
            gold -= groupOfItemsBaseDTO.getGold();
        } else {
            gold += groupOfItemsBaseDTO.getGold();
        }

        experience += groupOfItemsBaseDTO.getExperience();
    }


    private boolean ifHasFeedstockGiveIt(FeedstockDTO feedstockDTO, boolean payment) {
        for (FeedstockEntity userFeedstock : this.feedstocks) {
            if (userFeedstock.getIdentifier().equals(feedstockDTO.getId())) {
                if (payment) {
                    userFeedstock.removeQuantity(feedstockDTO.getQuantity());
                } else {
                    userFeedstock.insertQuantity(feedstockDTO.getQuantity());
                }
                return true;
            }
        }
        return false;
    }

    public boolean hasCraftItem(CraftItemDTO craftItemDTO) {
        return craftItems.stream().anyMatch(x -> (x.getIdentifier() == craftItemDTO.getId()) && (x.getType() == craftItemDTO.getType()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return steamId == that.steamId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(steamId);
    }

    @Override
    public String toString() {
        return String.valueOf(steamId);
    }
}
