package fact.it.airportservice;

import fact.it.airportservice.AirportRequest;
import fact.it.airportservice.AirlineResponse;
import fact.it.airportservice.Airport;
import fact.it.airportservice.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airport")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String requestFlights(@RequestBody AirportRequest airportRequest) {
        boolean result = airportService.requestFlights(airportRequest);
        return (result ? "successfully requested a flight" : "failed to request a flight");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AirportResponse> getAllAirports() {
        return airportService.getAllAirports();
    }
}
