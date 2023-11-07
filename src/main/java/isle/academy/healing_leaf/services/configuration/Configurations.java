package isle.academy.healing_leaf.services.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configurations {

    @Bean
    public RestTemplate GetRestTemplate(){
        return new RestTemplate();
    }

}
