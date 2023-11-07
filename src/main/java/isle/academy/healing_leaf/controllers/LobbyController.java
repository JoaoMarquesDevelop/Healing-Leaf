package isle.academy.healing_leaf.controllers;

import isle.academy.healing_leaf.controllers.generic.BaseApiV1Controller;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.lobby.request.CreateLobbyRequestDTO;
import isle.academy.healing_leaf.data.dto.lobby.request.EndLobbyRequestDTO;
import isle.academy.healing_leaf.data.dto.lobby.response.CreateLobbyResponseDTO;
import isle.academy.healing_leaf.services.contracts.ILobbyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static isle.academy.healing_leaf.data.StringsPackage.LOBBY_CONTROLLER_CREATE_LOBBY;
import static isle.academy.healing_leaf.data.StringsPackage.LOBBY_CONTROLLER_END_MATCH;


@Slf4j
@RestController
public class LobbyController extends BaseApiV1Controller {

    @Autowired
    private ILobbyService lobbyService;

    @PostMapping(path = "/lobby")
    @PreAuthorize("permitAll()")
    public CreateLobbyResponseDTO createLobby(@RequestAttribute long steamId,
                                              @Valid @RequestBody CreateLobbyRequestDTO createLobbyRequestDTO) {
        log.info(LOBBY_CONTROLLER_CREATE_LOBBY, steamId);
        return lobbyService.createLobby(steamId, createLobbyRequestDTO);
    }

    @PostMapping(path = "/lobby/endMatch")
    @PreAuthorize("permitAll()")
    public GroupOfItemsBaseDTO endMatch(@RequestAttribute long steamId,
                                        @Valid @RequestBody EndLobbyRequestDTO endLobbyRequestDTO) {
        log.info(LOBBY_CONTROLLER_END_MATCH, steamId);
        return lobbyService.endLobby(steamId, endLobbyRequestDTO);
    }
}
