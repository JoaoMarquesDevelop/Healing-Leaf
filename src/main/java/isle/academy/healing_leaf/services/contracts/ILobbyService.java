package isle.academy.healing_leaf.services.contracts;

import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.lobby.request.CreateLobbyRequestDTO;
import isle.academy.healing_leaf.data.dto.lobby.request.EndLobbyRequestDTO;
import isle.academy.healing_leaf.data.dto.lobby.response.CreateLobbyResponseDTO;

public interface ILobbyService {

    CreateLobbyResponseDTO createLobby(long steamId, CreateLobbyRequestDTO request);

    GroupOfItemsBaseDTO endLobby(long steamId, EndLobbyRequestDTO request);

}
