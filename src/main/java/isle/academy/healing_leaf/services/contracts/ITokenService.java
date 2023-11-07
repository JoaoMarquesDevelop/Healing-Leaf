package isle.academy.healing_leaf.services.contracts;

import io.jsonwebtoken.Claims;
import isle.academy.healing_leaf.data.dto.auth.TokenResponseDto;

import java.security.NoSuchAlgorithmException;

public interface ITokenService {

    TokenResponseDto getAuthenticationToken(String steamSessionId) throws NoSuchAlgorithmException;

    Claims decodeAuthenticationToken(String isleToken);
}
