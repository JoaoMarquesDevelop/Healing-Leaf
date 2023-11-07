package isle.academy.healing_leaf.controllers;

import isle.academy.healing_leaf.controllers.generic.BaseApiV1Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController extends BaseApiV1Controller {

    @GetMapping(path = "/status")
    public String getStatus() {
        return "OK";
    }
}
