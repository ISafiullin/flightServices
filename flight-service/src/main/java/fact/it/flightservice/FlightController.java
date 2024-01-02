package fact.it.flightservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createFlight
            (@RequestBody FlightRequest flightRequest) {
        flightService.createFlight(flightRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted successfully");
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