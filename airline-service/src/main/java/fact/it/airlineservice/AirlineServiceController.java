package fact.it.airlineservice;

import fact.it.airlineservice.AirlineResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineServiceController {
    private final AirlineService airlineService;

    @GetMapping
    @ResponseStatus
    public List<AirlineResponse> available
            (@RequestParam List<String> flightNumber){
        return airlineService.available(flightNumber);
    }

}
