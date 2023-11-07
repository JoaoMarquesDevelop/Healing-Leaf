package isle.academy.healing_leaf.services.contracts;

import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.user.request.StoryPointRequestDTO;

public interface IStoryService {

    GroupOfItemsBaseDTO nextStory(long steamId);

    FeedstockDTO retrieveStoryPoint(long steamId, StoryPointRequestDTO storyPointRequestDTO);
}
