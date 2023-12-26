package fact.it.airportservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/airport")
@RequiredArgsConstructor
public class AirportServiceController {

    private final AirportService airportService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String requestFlights(@RequestBody AirportRequest airportRequest) {
        boolean result = airportService.requestFlights(airportRequest);
        return (result ? "A flight has been requested" : "Thr flight couldn't be requested");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AirportResponse> getAllFlights(){
        return airportService.getAllAirports();
    }

}
