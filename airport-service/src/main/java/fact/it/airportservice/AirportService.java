package fact.it.airportservice;

import fact.it.airportservice.AirlineResponse;
import fact.it.airportservice.AirportLineFlightDto;
import fact.it.airportservice.AirportRequest;
import fact.it.airportservice.AirlineResponse;
import fact.it.airportservice.FlightResponse;
import fact.it.airportservice.Airport;
import fact.it.airportservice.AirportLineFlight;
import fact.it.airportservice.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AirportService {

    private final AirportRepository airportRepository;
    private final WebClient webClient;

    @Value("${flightservice.baseurl}")
    private String flightServiceBaseUrl;

    @Value("${airlineservice.baseurl}")
    private String airlineServiceBaseUrl;

    public boolean requestFlights(AirportRequest airportRequest) {
        Airport airport = new Airport();
        airport.setFlightRequestNumber(UUID.randomUUID().toString());

        List<AirportLineFlight> airportLineFlights = airportRequest.getAirportLineItemDtoList()
                .stream()
                .map(this::mapToAirportLineFlight)
                .toList();

        airport.setAirportLineFlightList(airportLineFlights);

        List<String> flightNumbers = airport.getAirportLineFlightList().stream()
                .map(AirportLineFlight::getFlightNumber)
                .toList();

        AirlineResponse[] airlineResponseArray = webClient.get()
                .uri("http://" + airlineServiceBaseUrl + "/api/airline",
                        uriBuilder -> uriBuilder.queryParam("flightnumber", flightNumbers).build())
                .retrieve()
                .bodyToMono(AirlineResponse[].class)
                .block();

        boolean allFlightsAvailable = Arrays.stream(airlineResponseArray)
                .allMatch(AirlineResponse::isAvailable);

        if(allFlightsAvailable){
            FlightResponse[] flightResponseArray = webClient.get()
                    .uri("http://" + flightServiceBaseUrl + "/api/flight",
                            uriBuilder -> uriBuilder.queryParam("flightnumber", flightNumbers).build())
                    .retrieve()
                    .bodyToMono(FlightResponse[].class)
                    .block();

            airport.getAirportLineFlightList().stream()
                    .map(airportFlight -> {
                        FlightResponse flight = Arrays.stream(flightResponseArray)
                                .filter(p -> p.getFlightNumber().equals(airportFlight.getFlightNumber()))
                                .findFirst()
                                .orElse(null);
                        if (flight != null) {
                            Integer bookedFlights = airport.getBookedFlights();
                            if (bookedFlights != null) {
                                airport.setBookedFlights(bookedFlights + 1);
                            } else {
                                airport.setBookedFlights(1);
                            }
                        }
                        return airportFlight;
                    })
                    .collect(Collectors.toList());

            airportRepository.save(airport);
            return true;
        } else {
            return false;
        }
    }

    public List<AirportResponse> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();

        return airports.stream()
                .map(airport -> new AirportResponse(
                        airport.getFlightRequestNumber(),
                        mapToAirportLineFlightDto(airport.getAirportLineFlightList())
                ))
                .collect(Collectors.toList());
    }

    private AirportLineFlight mapToAirportLineFlight(AirportLineFlightDto airportLineFlightDto) {
        AirportLineFlight airportLineFlight = new AirportLineFlight();
        airportLineFlight.setFlightNumber(airportLineFlightDto.getFlightNumber());
        airportLineFlight.setDestination(airportLineFlightDto.getDestination());
        return airportLineFlight;
    }

    private static List<AirportLineFlightDto> mapToAirportLineFlightDto(List<AirportLineFlight> airportLineFlights) {
        return airportLineFlights.stream()
                .map(airportLineFlight -> new AirportLineFlightDto(
                        airportLineFlight.getId(),
                        airportLineFlight.getFlightNumber(),
                        airportLineFlight.getDestination(),
                        airportLineFlight.getAvailableTickets()
                ))
                .collect(Collectors.toList());
    }
}
