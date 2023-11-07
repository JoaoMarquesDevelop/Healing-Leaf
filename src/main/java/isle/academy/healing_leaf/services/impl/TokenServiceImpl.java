package isle.academy.healing_leaf.services.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import isle.academy.healing_leaf.data.dto.auth.TokenResponseDto;
import isle.academy.healing_leaf.data.dto.auth.steam.SteamDataParamsDTO;
import isle.academy.healing_leaf.data.dto.auth.steam.SteamDataResponseDTO;
import isle.academy.healing_leaf.data.dto.auth.steam.SteamResponseDTO;
import isle.academy.healing_leaf.exceptions.AuthenticationException;
import isle.academy.healing_leaf.exceptions.JwtExpiredException;
import isle.academy.healing_leaf.services.contracts.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static isle.academy.healing_leaf.data.StringsPackage.*;

@Slf4j
@Service
public class TokenServiceImpl implements ITokenService {

    private static final String STEAM_REQUEST_URL = "https://api.steampowered.com/ISteamUserAuth/AuthenticateUserTicket/v1/?key=%s&appid=%s&ticket=%s";
    private static final String JWT_ISSUER = "Isle Tower Defense";
    private static Key signingKey;
    private static SignatureAlgorithm signatureAlgorithm;
    private static long expirationTimeInMillis;
    private static boolean steamInDevMode = false;
    private static String steamKey;
    private static String steamAppId;
    private static String jwtSecret;

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Autowired
    private TokenServiceImpl(Environment environment, RestTemplate restTemplate) {
        this.environment = environment;
        this.restTemplate = restTemplate;
        log.info(TOKEN_SERVICE_START);
    }

    @Override
    public TokenResponseDto getAuthenticationToken(String steamSessionId) {

        SteamDataParamsDTO steamData = RetrieveSteamId(steamSessionId);

        long nowMillis = System.currentTimeMillis();
        long expirationMillis = nowMillis + expirationTimeInMillis;
        Date expirationDate = new Date(expirationMillis);

        String jwtToken = createJWT(steamData.getSteamId(), expirationDate);

        log.info(TOKEN_SERVICE_GET_TOKEN_CALLED, steamData.getSteamId());

        return new TokenResponseDto(jwtToken, expirationMillis / 1000L);
    }

    @Override
    public Claims decodeAuthenticationToken(String jwt) {

        log.info(TOKEN_SERVICE_DECODE_TOKEN, jwt);

        Claims claims = null;
        //This line will throw an exception if it is not a signed JWS (as expected)
        try {
            claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encode(jwtSecret.getBytes()))
                    .parseClaimsJws(jwt).getBody();
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw new AuthenticationException(COULD_NOT_AUTHENTICATE_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException(EXPIRED_TOKEN);
        }

        return claims;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupJWTSignature() {

        log.info(INITIALIZING_TOKEN_SERVICE);

        //The JWT signature algorithm we will be using to sign the token
        signatureAlgorithm = SignatureAlgorithm.HS256;

        expirationTimeInMillis = Long.parseLong(Objects.requireNonNull(environment.getProperty("isle.security.jwt.expirationTimeInMillis")));

        steamInDevMode = environment.getProperty("isle.useTestUserForSteamCommunication", "false").equals("true");
        List sd = environment.getProperty("x", List.class);
        steamKey = environment.getProperty("isle.steamKey");

        steamAppId = environment.getProperty("isle.steamAppId");

        jwtSecret = environment.getProperty("isle.security.jwt.secret", "0");

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64.getEncoder().encode(jwtSecret.getBytes());

        signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    private static String createJWT(String steamId, Date expirationDate) {

        Date now = new Date();

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(steamId)
                .setIssuer(JWT_ISSUER)
                .signWith(signatureAlgorithm, signingKey);

        builder.setExpiration(expirationDate);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    private SteamDataParamsDTO RetrieveSteamId(String steamSessionId) {

        if (steamInDevMode) {
            return new SteamDataParamsDTO("OK", "123456789", "123456789", false, false);
        }

        String requestUrl = String.format(STEAM_REQUEST_URL, steamKey, steamAppId, steamSessionId);

        ResponseEntity<SteamDataResponseDTO> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        } catch (RestClientException e) {
            log.warn(e.getMessage());
            throw new AuthenticationException(COULD_NOT_AUTHENTICATE_WITH_STEAM);
        }

        if (responseEntity.getBody() == null) {
            log.warn(STEAM_NULL_RESPONSE_BODY);
            throw new AuthenticationException(COULD_NOT_AUTHENTICATE_WITH_STEAM);
        }

        SteamResponseDTO responseBody = responseEntity.getBody().getResponse();

        if (responseBody == null) {
            log.warn(STEAM_NULL_RESPONSE);
            throw new AuthenticationException(COULD_NOT_AUTHENTICATE_WITH_STEAM);
        }

        if (responseBody.getError() != null) {
            log.info(STEAM_ERROR_WITH_DESCRIPTION, responseBody.getError().getErrorCode(), responseBody.getError().getErrorDescription());
            throw new AuthenticationException(COULD_NOT_AUTHENTICATE_WITH_STEAM);
        }

        if (responseBody.getParams() == null) {
            log.warn(STEAM_RETURNING_NULL_PARAMS);
            throw new AuthenticationException(COULD_NOT_AUTHENTICATE_WITH_STEAM);
        }

        return responseBody.getParams();
    }
}
