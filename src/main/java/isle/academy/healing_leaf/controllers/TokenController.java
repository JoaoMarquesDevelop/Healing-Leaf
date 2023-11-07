package isle.academy.healing_leaf.controllers;

import isle.academy.healing_leaf.controllers.generic.BaseApiV1Controller;
import isle.academy.healing_leaf.data.dto.auth.TokenResponseDto;
import isle.academy.healing_leaf.services.contracts.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import static isle.academy.healing_leaf.data.StringsPackage.TOKEN_CONTROLLER_GET_TOKEN_CALLED;

@Slf4j
@RestController
public class TokenController extends BaseApiV1Controller {

    @Autowired
    private ITokenService tokenService;

    @GetMapping(path = "/token", params = "steamSessionId")
    public TokenResponseDto getAuthenticationToken(@RequestParam("steamSessionId") String steamSessionId)
            throws NoSuchAlgorithmException {
        log.info(TOKEN_CONTROLLER_GET_TOKEN_CALLED);
        return tokenService.getAuthenticationToken(steamSessionId);
    }
}
