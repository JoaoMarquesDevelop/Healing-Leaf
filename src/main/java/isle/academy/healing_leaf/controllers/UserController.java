package isle.academy.healing_leaf.controllers;

import isle.academy.healing_leaf.controllers.generic.BaseApiV1Controller;
import isle.academy.healing_leaf.controllers.generic.ControllerValidator;
import isle.academy.healing_leaf.data.dto.user.OtherUserResponseDTO;
import isle.academy.healing_leaf.data.dto.user.UserResponseDTO;
import isle.academy.healing_leaf.data.dto.user.request.LogoutRequestDTO;
import isle.academy.healing_leaf.services.contracts.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static isle.academy.healing_leaf.data.StringsPackage.*;

@Slf4j
@RestController
public class UserController extends BaseApiV1Controller {

    @Autowired
    private IUserService userService;

    @GetMapping(path = "/user")
    @PreAuthorize("permitAll()")
    public UserResponseDTO getUser(@RequestAttribute Long steamId) {
        log.info(USER_CONTROLLER_GET_USER_CALLED, steamId);
        return userService.getUser(steamId);
    }

    @GetMapping(path = "/otherUser/{steamId}")
    public OtherUserResponseDTO getOtherUser(@PathVariable("steamId") String stringSteamId) {
        log.info(USER_CONTROLLER_GET_OTHER_USER_CALLED, stringSteamId);
        long steamId = ControllerValidator.validateLong(stringSteamId);
        return userService.getOtherUser(steamId);
    }

    @PostMapping(path = "/logout")
    @PreAuthorize("permitAll()")
    public void logout(@RequestAttribute Long steamId,
                       @Valid @RequestBody LogoutRequestDTO logoutRequestDTO) {
        log.info(USER_CONTROLLER_LOGOUT_CALLED, steamId);
        userService.logoutRequest(steamId, logoutRequestDTO);
    }


}
