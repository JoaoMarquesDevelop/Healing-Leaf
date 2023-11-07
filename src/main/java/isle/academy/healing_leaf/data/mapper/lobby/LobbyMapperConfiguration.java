package isle.academy.healing_leaf.data.mapper.lobby;

import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.lobby.request.CreateLobbyRequestDTO;
import isle.academy.healing_leaf.data.entity.lobby.LobbyEntity;
import isle.academy.healing_leaf.data.entity.lobby.LobbyFeedstockEarningEntity;
import isle.academy.healing_leaf.data.enums.LobbyState;
import isle.academy.healing_leaf.data.mapper.BaseMapperConfiguration;
import isle.academy.healing_leaf.helpers.TimeHelper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class  LobbyMapperConfiguration extends BaseMapperConfiguration {

    @Autowired
    public LobbyMapperConfiguration(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void createTypeMappers() {

        Converter<CreateLobbyRequestDTO, LobbyEntity> lobbyEntityConverter = context -> {
            CreateLobbyRequestDTO request = context.getSource();

            return LobbyEntity.builder()
                    .lobbySteamId(request.getLobbySteamId())
                    .creationDate(TimeHelper.getCurrentTimeInIsleFormat())
                    .state(LobbyState.STARTED)
                    .stageId(request.getStageId())
                    .difficulty(request.getDifficulty())
                    .users(new HashSet<>())
                    .build();
        };

        mapper.addConverter(lobbyEntityConverter, CreateLobbyRequestDTO.class, LobbyEntity.class);

        Converter<LobbyEntity, GroupOfItemsBaseDTO> groupOfItemsBaseDTOConverter = context -> {
            LobbyEntity lobby = context.getSource();

            return GroupOfItemsBaseDTO.builder()
                    .gold(lobby.getGoldEarning())
                    .experience(lobby.getExperienceEarning())
                    .feedstocks(lobby.getFeedstocks()
                            .stream()
                            .map(x->mapper.map(x,FeedstockDTO.class))
                            .toList())
                    .craftItems(new HashSet<>()) // At the moment there is no way a lobby could give a craft item
                    .build();
        };

        mapper.addConverter(groupOfItemsBaseDTOConverter, LobbyEntity.class, GroupOfItemsBaseDTO.class);

        Converter<LobbyFeedstockEarningEntity, FeedstockDTO> lobbyEarningsConverter = context -> {
            LobbyFeedstockEarningEntity earningEntity = context.getSource();

            return FeedstockDTO.builder()
                    .id(earningEntity.getId())
                    .quantity(earningEntity.getQuantity())
                    .build();
        };

        mapper.addConverter(lobbyEarningsConverter, LobbyFeedstockEarningEntity.class, FeedstockDTO.class);
    }
}
