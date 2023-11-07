package isle.academy.healing_leaf.services.impl;

import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.lobby.data.CreatureDropData;
import isle.academy.healing_leaf.data.dto.lobby.data.StageData;
import isle.academy.healing_leaf.data.dto.lobby.request.CreateLobbyRequestDTO;
import isle.academy.healing_leaf.data.dto.lobby.request.EndLobbyRequestDTO;
import isle.academy.healing_leaf.data.dto.lobby.response.CreateLobbyResponseDTO;
import isle.academy.healing_leaf.data.dto.lobby.response.WaveRewardsResponseDTO;
import isle.academy.healing_leaf.data.entity.lobby.LobbyEntity;
import isle.academy.healing_leaf.data.entity.lobby.UserInLobbyEntity;
import isle.academy.healing_leaf.data.entity.user.UserEntity;
import isle.academy.healing_leaf.data.enums.LobbyState;
import isle.academy.healing_leaf.data.repository.LobbyRepository;
import isle.academy.healing_leaf.data.repository.UserRepository;
import isle.academy.healing_leaf.exceptions.EntityNotFoundException;
import isle.academy.healing_leaf.exceptions.ForbiddenException;
import isle.academy.healing_leaf.exceptions.InvalidRequestException;
import isle.academy.healing_leaf.helpers.JsonDataRetriever;
import isle.academy.healing_leaf.helpers.LobbyRewardsCalculator;
import isle.academy.healing_leaf.services.contracts.ILobbyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static isle.academy.healing_leaf.data.StringsPackage.*;

@Slf4j
@Service
public class LobbyServiceImpl implements ILobbyService {

    private final UserRepository userRepository;
    private final LobbyRepository lobbyRepository;
    private final ModelMapper mapper;
    private final Environment environment;

    private StageData[] stagesData;
    private static final String CREATURES_IN_STAGE_PATH = "/data/CreaturesInStage.json";

    // future we can use this to display to the web
    private CreatureDropData[] creatureDropData;
    private static final String CREATURES_DROP_DATA = "/data/CreatureDrops.json";

    private float dropChanceMultiplier;


    public LobbyServiceImpl(UserRepository userRepository, LobbyRepository lobbyRepository, ModelMapper mapper, Environment environment) {
        this.userRepository = userRepository;
        this.lobbyRepository = lobbyRepository;
        this.mapper = mapper;
        this.environment = environment;
        log.info(LOBBY_SERVICE_START);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupStageData() {
        stagesData = JsonDataRetriever.retrieveFromJson(CREATURES_IN_STAGE_PATH, StageData[].class);
        creatureDropData = JsonDataRetriever.retrieveFromJson(CREATURES_DROP_DATA, CreatureDropData[].class);
        for (StageData stage : stagesData) {
            stage.SetCreatures(creatureDropData);
        }
        dropChanceMultiplier = Float.parseFloat(Objects.requireNonNull(environment.getProperty("isle.dropChanceMultiplier")));
        log.info(LOBBY_SERVICE_SETUP_STAGE_DATA);
    }

    @Override
    public CreateLobbyResponseDTO createLobby(long steamId, CreateLobbyRequestDTO request) {

        // Validations
        for (long userInLobby : request.getOtherUsersInLobby()) {
            if (userInLobby == steamId) {
                throw new InvalidRequestException(LOBBY_CREATOR_IN_LOBBY);
            }
        }

        if (request.getStageId() > stagesData.length) {
            throw new InvalidRequestException(MUST_BE_AT_MAX + stagesData.length);
        }

        Optional<UserEntity> dbUser = userRepository.findById(steamId);

        if (dbUser.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND + steamId);
        }

        Optional<LobbyEntity> dbLobby = lobbyRepository.findById(request.getLobbySteamId());

        if (dbLobby.isPresent()) {
            throw new InvalidRequestException(LOBBY_ALREADY_EXISTS + request.getLobbySteamId());
        }

        LobbyEntity lobby = mapper.map(request, LobbyEntity.class);
        lobby.setOwner(dbUser.get());

        // Add other users to lobby
        for (long userId : request.getOtherUsersInLobby()) {
            Optional<UserEntity> lobbyUser = userRepository.findById(userId);

            if (lobbyUser.isEmpty()) {
                throw new EntityNotFoundException(USER_NOT_FOUND + userId);
            }

            UserInLobbyEntity userInLobby = UserInLobbyEntity.builder()
                    .lobby(lobby)
                    .user(lobbyUser.get())
                    .build();

            lobby.addNewUser(userInLobby);
        }

        List<WaveRewardsResponseDTO> waveResponses = LobbyRewardsCalculator
                .CalculateLobbyDrops(stagesData[request.getStageId() - 1],
                        request.getDifficulty(),
                        lobby,
                        dropChanceMultiplier);

        lobbyRepository.save(lobby);

        log.info(LOBBY_SERVICE_CREATE_LOBBY, lobby);

        return CreateLobbyResponseDTO
                .builder()
                .waves(waveResponses)
                .build();
    }

    @Override
    public GroupOfItemsBaseDTO endLobby(long steamId, EndLobbyRequestDTO request) {

        Optional<LobbyEntity> dbLobby = lobbyRepository.findById(request.getLobbyId());

        if (dbLobby.isEmpty()) {
            throw new EntityNotFoundException(LOBBY_NOT_FOUND + request.getLobbyId());
        }

        if (dbLobby.get().getState() == LobbyState.LOST || dbLobby.get().getState() == LobbyState.WON) {
            throw new InvalidRequestException(LOBBY_ALREADY_ENDED + request.getLobbyId());
        }

        if (dbLobby.get().getOwner().getSteamId() != steamId) {
            throw new ForbiddenException(USER_NOT_ALLOWED_TO_END_LOBBY + request.getLobbyId());
        }

        GroupOfItemsBaseDTO rewards = dbLobby.get().endLobby(request.isWon(), mapper);

        lobbyRepository.save(dbLobby.get());

        log.info(LOBBY_SERVICE_END_LOBBY, dbLobby.get().getLobbySteamId(), steamId);

        return rewards;
    }
}
