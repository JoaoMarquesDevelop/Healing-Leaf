package isle.academy.healing_leaf.data.mapper.user;

import isle.academy.healing_leaf.data.dto.items.CraftItemDTO;
import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.user.OtherUserResponseDTO;
import isle.academy.healing_leaf.data.dto.user.UserResponseDTO;
import isle.academy.healing_leaf.data.entity.user.StoryPointEntity;
import isle.academy.healing_leaf.data.entity.user.UserEntity;
import isle.academy.healing_leaf.data.mapper.BaseMapperConfiguration;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Configuration
public class UserMapperConfiguration extends BaseMapperConfiguration {

    @Autowired
    public UserMapperConfiguration(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void createTypeMappers() {
        Converter<UserEntity, UserResponseDTO> userResponseConverter = context -> {
            UserEntity userEntity = context.getSource();

            return UserResponseDTO.builder()
                    .steamId(userEntity.getSteamId())
                    .gold(userEntity.getGold())
                    .experience(userEntity.getExperience())
                    .positionX(userEntity.getPositionX())
                    .positionY(userEntity.getPositionY())
                    .positionZ(userEntity.getPositionZ())
                    .storyIndex(userEntity.getStoryIndex())
                    .soundtrackVolume(userEntity.getSoundtrackVolume())
                    .uiVolume(userEntity.getUiVolume())
                    .fightVolume(userEntity.getFightVolume())
                    .currentIsland(userEntity.getCurrentIsland())
                    .feedstocks(userEntity.getFeedstocks() == null ?
                            new ArrayList<>() :
                            userEntity.getFeedstocks()
                                    .stream()
                                    .map(x -> mapper.map(x, FeedstockDTO.class))
                                    .toList())
                    .craftItems(userEntity.getCraftItems() == null ?
                            new HashSet<>() :
                            userEntity.getCraftItems()
                                    .stream()
                                    .map(x -> mapper.map(x, CraftItemDTO.class))
                                    .collect(Collectors.toSet()))
                    .storyPoints(userEntity.getStoryPoints() == null ?
                            new ArrayList<>() :
                            userEntity.getStoryPoints()
                                    .stream()
                                    .map(StoryPointEntity::getIdentifier)
                                    .toList())
                    .build();
        };

        mapper.addConverter(userResponseConverter, UserEntity.class, UserResponseDTO.class);

        Converter<UserEntity, OtherUserResponseDTO> otherUserResponseConverter = context -> {
            UserEntity userEntity = context.getSource();

            return OtherUserResponseDTO.builder()
                    .experience(userEntity.getExperience())
                    .build();
        };

        mapper.addConverter(otherUserResponseConverter, UserEntity.class, OtherUserResponseDTO.class);

        Converter<UserEntity, GroupOfItemsBaseDTO> GroupOfItemsConverter = context -> {
            UserEntity userEntity = context.getSource();

            return GroupOfItemsBaseDTO.builder()
                    .gold(userEntity.getGold())
                    .experience(userEntity.getExperience())
                    .feedstocks(userEntity.getFeedstocks()
                            .stream()
                            .map(x -> mapper.map(x, FeedstockDTO.class))
                            .toList())
                    .craftItems(userEntity.getCraftItems()
                            .stream()
                            .map(x -> mapper.map(x, CraftItemDTO.class))
                            .collect(Collectors.toSet()))
                    .build();
        };

        mapper.addConverter(userResponseConverter, UserEntity.class, UserResponseDTO.class);
    }
}