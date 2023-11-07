package isle.academy.healing_leaf.controllers;

import isle.academy.healing_leaf.controllers.generic.BaseApiV1Controller;
import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.user.request.StoryPointRequestDTO;
import isle.academy.healing_leaf.services.contracts.IStoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static isle.academy.healing_leaf.data.StringsPackage.STORY_CONTROLLER_INCREMENT_STORY;
import static isle.academy.healing_leaf.data.StringsPackage.STORY_CONTROLLER_RETRIEVE_STORY_POINT;


@Slf4j
@RestController
public class StoryController extends BaseApiV1Controller {

    @Autowired
    private IStoryService storyService;

    @PostMapping(path = "/nextStory")
    @PreAuthorize("permitAll()")
    public GroupOfItemsBaseDTO nextStory(@RequestAttribute long steamId) {
        log.info(STORY_CONTROLLER_INCREMENT_STORY, steamId);
        return storyService.nextStory(steamId);
    }

    @PostMapping(path = "/storyPoint")
    @PreAuthorize("permitAll()")
    public FeedstockDTO retrieveStoryPoint(@RequestAttribute long steamId,
                                           @Valid @RequestBody StoryPointRequestDTO storyPointRequestDTO) {
        log.info(STORY_CONTROLLER_RETRIEVE_STORY_POINT, steamId, storyPointRequestDTO.getId());
        return storyService.retrieveStoryPoint(steamId, storyPointRequestDTO);
    }
}
