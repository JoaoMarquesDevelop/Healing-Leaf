package isle.academy.healing_leaf.services.impl;

import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.user.request.StoryPointRequestDTO;
import isle.academy.healing_leaf.data.entity.user.UserEntity;
import isle.academy.healing_leaf.data.repository.UserRepository;
import isle.academy.healing_leaf.exceptions.EntityNotFoundException;
import isle.academy.healing_leaf.exceptions.InvalidRequestException;
import isle.academy.healing_leaf.helpers.JsonDataRetriever;
import isle.academy.healing_leaf.services.contracts.IStoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static isle.academy.healing_leaf.data.StringsPackage.*;

@Slf4j
@Service
public class StoryServiceImpl implements IStoryService {

    private final UserRepository userRepository;

    private GroupOfItemsBaseDTO[] rewardsOnEachStory;
    private static final String STORY_REWARDS_DATA_PATH = "/data/StoryRewards.json";

    private FeedstockDTO[] rewardsOnEachStoryPoint;
    private static final String STORY_POINT_REWARDS_DATA_PATH = "/data/StoryPointsRewards.json";

    private final ModelMapper mapper;

    public StoryServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        log.info(STORY_SERVICE_START);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupStoryRewards() {
        rewardsOnEachStory = JsonDataRetriever.retrieveFromJson(STORY_REWARDS_DATA_PATH, GroupOfItemsBaseDTO[].class);
        rewardsOnEachStoryPoint = JsonDataRetriever.retrieveFromJson(STORY_POINT_REWARDS_DATA_PATH, FeedstockDTO[].class);
        log.info(STORY_SERVICE_SETUP_STORY_REWARDS);
    }

    @Override
    public GroupOfItemsBaseDTO nextStory(long steamId) {
        Optional<UserEntity> dbUser = userRepository.findById(steamId);

        if (dbUser.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }

        int currentStory = dbUser.get().nextStory();

        log.info(STORY_SERVICE_INCREMENT_STORY, steamId, currentStory);

        GroupOfItemsBaseDTO rewardsToGiveToTheUser;

        if (currentStory < 0 || currentStory > rewardsOnEachStory.length) {
            rewardsToGiveToTheUser = new GroupOfItemsBaseDTO();
        } else {
            rewardsToGiveToTheUser = rewardsOnEachStory[currentStory - 1];
        }

        dbUser.get().giveReward(rewardsToGiveToTheUser, mapper, false);

        userRepository.save(dbUser.get());
        return rewardsToGiveToTheUser;
    }

    @Override
    public FeedstockDTO retrieveStoryPoint(long steamId, StoryPointRequestDTO storyPointRequestDTO) {

        if (storyPointRequestDTO.getId() > rewardsOnEachStoryPoint.length) {
            throw new InvalidRequestException(MAX_VALUE_FOR_STORY_POINT_ID + rewardsOnEachStoryPoint.length);
        }

        Optional<UserEntity> dbUser = userRepository.findById(steamId);

        if (dbUser.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }

        boolean addedStoryPoint = dbUser.get().insertNewStoryPoint(storyPointRequestDTO.getId());

        if (!addedStoryPoint) {
            log.warn(STORY_SERVICE_RETRIEVE_REPEATED_STORY, steamId, storyPointRequestDTO.getId());
            return null;
        }

        FeedstockDTO feedstockToGiveToUser = rewardsOnEachStoryPoint[storyPointRequestDTO.getId() - 1];

        GroupOfItemsBaseDTO groupOfItemsBaseDTO = GroupOfItemsBaseDTO.builder()
                .feedstocks(List.of(feedstockToGiveToUser))
                .build();

        dbUser.get().giveReward(groupOfItemsBaseDTO, mapper, false);

        userRepository.save(dbUser.get());

        return feedstockToGiveToUser;
    }

}
