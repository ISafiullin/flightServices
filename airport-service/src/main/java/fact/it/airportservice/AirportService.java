package fact.it.airportservice;

import fact.it.airportservice.AirlineResponse;
import fact.it.airportservice.AirportLineFlightDto;
import fact.it.airportservice.AirportRequest;
import fact.it.airportservice.AirlineResponse;
import fact.it.airportservice.FlightResponse;
import fact.it.airportservice.Airport;
import fact.it.airportservice.AirportLineFlight;
import fact.it.airportservice.AirportRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    @PostConstruct
    public void loadData() {
        if (airportRepository.count() <= 0) {
            Airport airport = new Airport();
            airport.setName("JFK Airport");
            airport.setFlightRequestNumber("JFK 111");  // Set the status here
            airport.setBookedFlights(55);

            Airport airport1 = new Airport();
            airport1.setName("Brussels Airport");
            airport1.setFlightRequestNumber("BRU 000");  // Set the status here
            airport1.setBookedFlights(30);

            Airport airport2 = new Airport();
            airport2.setName("Schiphol");
            airport2.setFlightRequestNumber("SCH 234");  // Set the status here
            airport2.setBookedFlights(48);

            airportRepository.save(airport);
            airportRepository.save(airport1);
            airportRepository.save(airport2);
        }
    }

    public boolean requestFlights(AirportRequest airportRequest) {
        // Create a new Airport object and set a random flight request number
        Airport airport = new Airport();
        airport.setFlightRequestNumber(UUID.randomUUID().toString());

        // Map the AirportLineFlightDtoList from the AirportRequest to AirportLineFlights
        List<AirportLineFlight> airportLineFlights = airportRequest.getAirportLineFlightDtoList()
                .stream()
                .map(this::mapToAirportLineFlight)
                .toList();

        // Set the AirportLineFlightsList for the airport
        airport.setAirportLineFlightsList(airportLineFlights);

        // Extract flight numbers from the AirportLineFlightsList
        List<String> flightNumbers = airport.getAirportLineFlightsList().stream()
                .map(AirportLineFlight::getFlightNumber)
                .toList();

        // Fetch availability information from the airline service
        AirlineResponse[] airlineResponseArray = webClient.get()
                .uri("http://" + airlineServiceBaseUrl + "/api/airline",
                        uriBuilder -> uriBuilder.queryParam("flightnumber", flightNumbers).build())
                .retrieve()
                .bodyToMono(AirlineResponse[].class)
                .block();

        // Check if all flights are available
        boolean allFlightsAvailable = Arrays.stream(airlineResponseArray)
                .allMatch(AirlineResponse::isAvailable);

        if (allFlightsAvailable) {
            // Fetch detailed flight information from the flight service
            FlightResponse[] flightResponseArray = webClient.get()
                    .uri("http://" + flightServiceBaseUrl + "/api/flight",
                            uriBuilder -> uriBuilder.queryParam("flightnumber", flightNumbers).build())
                    .retrieve()
                    .bodyToMono(FlightResponse[].class)
                    .block();

            // Update bookedFlights count for each flight
            airport.getAirportLineFlightsList().stream()
                    .map(airportFlight -> {
                        FlightResponse flight = Arrays.stream(flightResponseArray)
                                .filter(p -> p.getFlightNumber().equals(airportFlight.getFlightNumber()))
                                .findFirst()
                                .orElse(null);

                        if (flight != null) {
                            // Increment bookedFlights count for the corresponding airport
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

            // Save the updated airport information
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
                        airport.getName(),
                        airport.getFlightRequestNumber(),
                        mapToAirportLineFlightDto(airport.getAirportLineFlightsList())
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
                        airportLineFlight.getAvailableTickets(),
                        airportLineFlight.getGateNumber()
                ))
                .collect(Collectors.toList());
    }

    public boolean assignGateToFlight(AirportLineFlightDto flightDto) {
        return airportRepository.findById(flightDto.getId())
                .map(airport -> {
                    AirportLineFlight matchingFlight = airport.getAirportLineFlightsList().stream()
                            .filter(flight -> flight.getFlightNumber().equals(flightDto.getFlightNumber()) && !flight.isAssignedGate())
                            .findFirst()
                            .orElse(null);

                    if (matchingFlight != null) {
                        matchingFlight.setGateNumber(flightDto.getGateNumber());
                        matchingFlight.setAssignedGate(true);
                        airportRepository.save(airport);
                        return true;
                    } else {
                        return false; // Flight is already assigned a gate
                    }
                })
                .orElse(false); // Airport not found
    }



}
