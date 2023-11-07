package isle.academy.healing_leaf.security;

import io.jsonwebtoken.Claims;
import isle.academy.healing_leaf.exceptions.AuthenticationException;
import isle.academy.healing_leaf.exceptions.JwtExpiredException;
import isle.academy.healing_leaf.services.contracts.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static isle.academy.healing_leaf.data.StringsPackage.CANNOT_RETRIEVE_VALID_DATA_FROM_STEAM;
import static isle.academy.healing_leaf.data.StringsPackage.MISSING_TOKEN;

@Slf4j
public class JwtDataExtractor extends OncePerRequestFilter {

    public static String ATTRIBUTE_STEAM_ID = "steamId";

    private final ITokenService tokenService;

    private final Environment environment;

    public JwtDataExtractor(ITokenService tokenService, Environment environment) {
        this.tokenService = tokenService;
        this.environment = environment;
    }


    /**
     * If this is called it means we have a valid token.
     * we extract the data from the jwt and attach to the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtString = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtString == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(MISSING_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        jwtString = jwtString.replace("Bearer ", "");

        Claims claims = null;

        // takes care of expiration time too
        try{
            claims = tokenService.decodeAuthenticationToken(jwtString);
        } catch (AuthenticationException | JwtExpiredException authenticationException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(authenticationException.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        String steamId = claims.getSubject();

        long longSteamId = 0;

        try{
            longSteamId = Long.parseLong(steamId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().println(CANNOT_RETRIEVE_VALID_DATA_FROM_STEAM);
            filterChain.doFilter(request, response);
            return;
        }

        request.setAttribute(ATTRIBUTE_STEAM_ID, longSteamId);
        filterChain.doFilter(request, response);
    }
}
