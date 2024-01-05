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

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    public String requestFlights(@RequestBody AirportRequest airportRequest) {
        boolean result = airportService.requestFlights(airportRequest);
        return (result ? "Successfully requested a flight" : "Failed to request a flight");
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AirportResponse> getAllAirports() {
        return airportService.getAllAirports();
    }

    @PostMapping("/assignGate")
    @ResponseStatus(HttpStatus.OK)
    public String assignGateToFlight(@RequestBody AirportLineFlightDto flightDto) {
        boolean result = airportService.assignGateToFlight(flightDto);
        return (result ? "Gate assigned successfully" : "Failed to assign gate");
    }
}