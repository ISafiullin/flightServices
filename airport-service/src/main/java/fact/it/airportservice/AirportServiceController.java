package fact.it.airportservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airport-service")
@RequiredArgsConstructor
public class AirportServiceController {
    private final AirportService airportService;

    @PostMapping("/request-flight")
    @ResponseStatus(HttpStatus.OK)
    public String requestFlights(@RequestBody AirportRequest airportRequest) {
        boolean result = airportService.requestFlights(airportRequest);
        return (result ? "A flight has been requested" : "The flight couldn't be requested");
    }

    @GetMapping("/all-flights")
    @ResponseStatus(HttpStatus.OK)
    public List<AirportResponse> getAllFlights() {
        return airportService.getAllAirports();
    }
}