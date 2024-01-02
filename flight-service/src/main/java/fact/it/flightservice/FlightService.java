package fact.it.flightservice;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    @PostConstruct
    public void loadData() {
        if(flightRepository.count() <= 0){
            Flight flight = Flight.builder()
                    .flightNumber("AA123")
                    .destination("New York")
                    .availableTickets(484)
                    .build();

            Flight flight1 = Flight.builder()
                    .flightNumber("DK545")
                    .destination("Frankfurt")
                    .availableTickets(186)
                    .build();

            Flight flight2 = Flight.builder()
                    .flightNumber("FF112")
                    .destination("Milan")
                    .availableTickets(294)
                    .build();

            flightRepository.save(flight);
            flightRepository.save(flight1);
            flightRepository.save(flight2);
        }
    }

    public void createFlight(FlightRequest flightRequest) {
        Flight flight = Flight.builder()
                .flightNumber(flightRequest.getFlightNumber())
                .destination(flightRequest.getDestination())
                .availableTickets(flightRequest.getAvailableTickets())
                .build();

        flightRepository.save(flight);
    }

    public List<FlightResponse> getAllFlightsByFlightNumber(List<String> flightNumber) {
        List<Flight> flights = flightRepository.findByFlightNumberIn(flightNumber);

        return flights.stream().map(this::mapToFlightResponse).toList();
    }

    private FlightResponse mapToFlightResponse(Flight flight) {
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .destination(flight.getDestination())
                .availableTickets(flight.getAvailableTickets())
                .build();
    }

    public List<FlightResponse> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();

        return flights.stream().map(this::mapToFlightResponse).toList();
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

}
