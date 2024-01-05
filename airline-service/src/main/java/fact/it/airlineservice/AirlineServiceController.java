package fact.it.airlineservice;

import fact.it.airlineservice.AirlineResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineServiceController {
    private final AirlineService airlineService;

    @GetMapping("/availabel")
    @ResponseStatus
    public List<AirlineResponse> available
            (@RequestParam List<String> flightNumber){
        return airlineService.available(flightNumber);
    }

    @PostMapping("/cancelFlight/{id}")
    public ResponseEntity<String> cancelFlight(@PathVariable Long id) {
        boolean result = airlineService.cancelFlight(id);

        if (result) {
            return ResponseEntity.ok("Flight canceled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found or already canceled");
        }
    }

    @PostMapping("/scheduleFlight/{id}")
    public ResponseEntity<String> scheduleFlight(@PathVariable Long id) {
        boolean result = airlineService.scheduleFlight(id);

        if (result) {
            return ResponseEntity.ok("Flight scheduled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not successfully scheduled");
        }
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AirlineResponse> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

}
