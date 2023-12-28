package fact.it.flightservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/flight")
public class FlightServiceController{

    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createFlight(@RequestBody FlightRequest flightRequest) {
        flightService.createFlight(flightRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getAllProductsBySkuCode
            (@RequestParam List<String> destination) {
        return flightService.getAllFlightsByDestination(destination);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getAllFlights() {
        return flightService.getAllFlights();
    }

}
