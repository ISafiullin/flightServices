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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createFlight(@RequestBody FlightRequest flightRequest) {
        flightService.createFlight(flightRequest);

        // Respond with a success message
        return ResponseEntity.status(HttpStatus.CREATED).body("Flight created successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted successfully");
    }

    @GetMapping("/{flightNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getAllFlightsByFlightNumber
            (@PathVariable List<String> flightNumber) {
        return flightService.getAllFlightsByFlightNumber(flightNumber);
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResponse> getAllFlights() {
        return flightService.getAllFlights();
    }
}