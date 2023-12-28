package fact.it.flightservice;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<FlightResponse> getAllFlightsByDestination(List<String> destination) {
        List<Flight> flights = flightRepository.findByDestinationIn(destination);

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
        List<Flight> products = flightRepository.findAll();

        return products.stream().map(this::mapToFlightResponse).toList();
    }
}
