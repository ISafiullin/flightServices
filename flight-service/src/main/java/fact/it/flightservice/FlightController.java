package fact.it.flightservice;

import fact.it.flightservice.FlightRequest;
import fact.it.flightservice.FlightResponse;
import fact.it.flightservice.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void registerFlight
            (@RequestBody FlightRequest flightRequest) {
        flightService.createFlight(flightRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getAllFlightsByFlightNumber
            (@RequestParam List<String> flightNumber) {
        return flightService.getAllFlightsByFlightNumber(flightNumber);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getAllFlights() {
        return flightService.getAllFlights();
    }
}