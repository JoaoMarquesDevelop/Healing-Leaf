package isle.academy.healing_leaf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static isle.academy.healing_leaf.data.StringsPackage.START_ISLE_TD;

@SpringBootApplication
@Slf4j
public class HealingLeafApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealingLeafApplication.class, args);
        log.info(START_ISLE_TD);
    }

}
