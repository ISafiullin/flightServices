package fact.it.airline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/airlines")
public record AirlineServiceController(AirlineService airlineService) {

    @PostMapping
    public void registerAirline(@RequestBody AirlineRegistrationRequest airlineRegistrationRequest) {
        log.info("new airline registration {}", airlineRegistrationRequest);
        airlineService.registerAirline(airlineRegistrationRequest);
    }
}
