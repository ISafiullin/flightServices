package fact.it.airlineservice;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirlineService {

    final private AirlineRepository airlineRepository;
    @PostConstruct
    public void loadData() {
        if(airlineRepository.count() <= 0){
            AirlineFlight airlineFlight = new AirlineFlight();
            airlineFlight.setName("TUI");
            airlineFlight.setFlightNumber("AA123");
            airlineFlight.setStatus("SCHEDULED");
            airlineFlight.setAvailable(true);

            AirlineFlight airlineFlight1 = new AirlineFlight();
            airlineFlight1.setName("Brussels Airlines");
            airlineFlight1.setFlightNumber("DK545");
            airlineFlight1.setStatus("SCHEDULED");
            airlineFlight1.setAvailable(true);

            AirlineFlight airlineFlight2 = new AirlineFlight();
            airlineFlight2.setName("RyanAir");
            airlineFlight2.setFlightNumber("FF112");
            airlineFlight.setStatus("SCHEDULED");
            airlineFlight2.setAvailable(true);

            airlineRepository.save(airlineFlight);
            airlineRepository.save(airlineFlight1);
            airlineRepository.save(airlineFlight2);
        }
    }

    @Transactional(readOnly = true)
    public List<AirlineResponse> available(List<String> flightNumber) {

        return airlineRepository.findByFlightNumberIn(flightNumber).stream()
                .map(airlineFlight ->
                        AirlineResponse.builder()
                                .flightNumber(airlineFlight.getFlightNumber())
                                .available(true)
                                .build()
                ).toList();
    }

    public boolean cancelFlight(Long id) {
        Optional<AirlineFlight> optionalFlight = airlineRepository.findById(id);

        if (optionalFlight.isPresent()) {
            AirlineFlight flight = optionalFlight.get();

            // Check if the flight is not already canceled
            if (!"CANCELLED".equals(flight.getStatus())) {
                // Update the flight status to canceled
                flight.setStatus("CANCELLED");
                // Set available to false
                flight.setAvailable(false);

                airlineRepository.save(flight);
                return true; // Flight canceled successfully
            } else {
                return false; // Flight is already canceled
            }
        } else {
            return false; // Flight not found
        }
    }
}
