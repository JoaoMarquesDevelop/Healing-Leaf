package isle.academy.healing_leaf.security;

import isle.academy.healing_leaf.services.contracts.ITokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final ITokenService tokenService;

    private final Environment environment;

    public SecurityConfiguration(ITokenService tokenService, Environment environment) {
        this.tokenService = tokenService;
        this.environment = environment;
    }

    /**
     * This bean ensures all requests require authentication.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .regexMatcher("^((?!/token)(?!.*status)(?!/otherUser/)(?!/items).)*$")
                .addFilterAfter(new JwtDataExtractor(tokenService, environment), BasicAuthenticationFilter.class);


        return http.build();
    }
}
