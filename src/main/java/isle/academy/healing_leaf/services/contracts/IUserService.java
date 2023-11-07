package isle.academy.healing_leaf.services.contracts;

import isle.academy.healing_leaf.data.dto.user.OtherUserResponseDTO;
import isle.academy.healing_leaf.data.dto.user.UserResponseDTO;
import isle.academy.healing_leaf.data.dto.user.request.LogoutRequestDTO;

public interface IUserService {

    UserResponseDTO getUser(Long steamId);

    OtherUserResponseDTO getOtherUser(Long steamId);

    void logoutRequest(Long steamId, LogoutRequestDTO logoutRequestDTO);
}
