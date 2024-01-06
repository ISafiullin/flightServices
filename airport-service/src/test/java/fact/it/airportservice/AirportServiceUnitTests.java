package fact.it.airportservice;

import fact.it.airportservice.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportServiceUnitTests {

    @InjectMocks
    private AirportService airportService;

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(airportService, "flightServiceBaseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(airportService, "airlineServiceBaseUrl", "http://localhost:8082");
    }

    @Test
    public void testRequestFlight_Success() {
        // Arrange

        String flightNumber = "ABC 123";
        String destination = "Brussels";
        Integer availableTickets = 484;

        AirportRequest airportRequest = new AirportRequest();
        // populate orderRequest with test data
        AirportLineFlightDto airportLineFlightDto = new AirportLineFlightDto();
        airportLineFlightDto.setId(1L);
        airportLineFlightDto.setFlightNumber(flightNumber);
        airportLineFlightDto.setDestination(destination);
        airportLineFlightDto.setAvailableTickets(484);
        airportRequest.setAirportLineFlightDtoList(Arrays.asList(airportLineFlightDto));

        AirlineResponse airlineResponse = new AirlineResponse();
        // populate inventoryResponse with test data
        airlineResponse.setFlightNumber(flightNumber);
        airlineResponse.setAvailable(true);

        FlightResponse flightResponse = new FlightResponse();
        // populate productResponse with test data
        flightResponse.setId(1L);
        flightResponse.setFlightNumber(flightNumber);
        flightResponse.setDestination(destination);
        flightResponse.setAvailableTickets(484);

        Airport airport = new Airport();
        airport.setId(1L);
        airport.setFlightRequestNumber("1");
        airport.setBookedFlights(0);
        AirportLineFlight airportLineFlight = new AirportLineFlight();
        airportLineFlight.setId(1L);
        airportLineFlight.setFlightNumber(flightNumber);
        airportLineFlight.setDestination(destination);
        airportLineFlight.setAvailableTickets(484);
        airport.setAirportLineFlightsList(Arrays.asList(airportLineFlight));

        when(airportRepository.save(any(Airport.class))).thenReturn(airport);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(),  any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(AirlineResponse[].class)).thenReturn(Mono.just(new AirlineResponse[]{airlineResponse}));
        when(responseSpec.bodyToMono(FlightResponse[].class)).thenReturn(Mono.just(new FlightResponse[]{flightResponse}));

        // Act
        boolean result = airportService.requestFlights(airportRequest);

        // Assert
        assertTrue(result);

        verify(airportRepository, times(1)).save(any(Airport.class));
    }

    @Test
    public void testRequestFlights_Failure() {
        // Arrange

        String flightNumber = "ABC 123";
        String destination = "Brussels";


        AirportRequest airportRequest = new AirportRequest();
        // populate orderRequest with test data
        AirportLineFlightDto airportLineFlightDto = new AirportLineFlightDto();
        airportLineFlightDto.setId(1L);
        airportLineFlightDto.setFlightNumber(flightNumber);
        airportLineFlightDto.setDestination(destination);
        airportLineFlightDto.setAvailableTickets(484);
        airportRequest.setAirportLineFlightDtoList(Arrays.asList(airportLineFlightDto));

        AirlineResponse airlineResponse = new AirlineResponse();
        // populate inventoryResponse with test data
        airlineResponse.setFlightNumber(flightNumber);
        airlineResponse.setAvailable(false);

        FlightResponse flightResponse = new FlightResponse();
        // populate productResponse with test data
        flightResponse.setId(1L);
        flightResponse.setFlightNumber(flightNumber);
        flightResponse.setDestination(destination);
        flightResponse.setAvailableTickets(484);

        Airport airport = new Airport();
        airport.setId(1L);
        airport.setFlightRequestNumber("1");
        airport.setName("JFK Airport");
        airport.setBookedFlights(1);
        AirportLineFlight airportLineFlight = new AirportLineFlight();
        airportLineFlight.setId(1L);
        airportLineFlight.setFlightNumber(flightNumber);
        airportLineFlight.setDestination(destination);
        airportLineFlight.setAvailableTickets(484);
        airport.setAirportLineFlightsList(Arrays.asList(airportLineFlight));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(),  any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(AirlineResponse[].class)).thenReturn(Mono.just(new AirlineResponse[]{airlineResponse}));

        // Act
        boolean result = airportService.requestFlights(airportRequest);

        // Assert
        assertFalse(false);

        verify(airportRepository, times(0)).save(airport);
    }

    @Test
    public void testGetAllAirports() {
        // Arrange
        AirportLineFlight airportLineFlight = new AirportLineFlight(1L, "ABC 123", "Brussels",484,null,false);
        AirportLineFlight airportLineFlight1 = new AirportLineFlight(2L, "DEF 456", "Amsterdam",484, null, false);

        Airport airport1 = new Airport(1L, "JFK Airport", "1",2,Arrays.asList(airportLineFlight1, airportLineFlight));

        AirportLineFlight airportLineFlight2 = new AirportLineFlight(3L, "XYZ 999", "Milan", 484,"5B",true);
        AirportLineFlight airportLineFlight3 = new AirportLineFlight(4L, "UFC 229", "Hamburg", 484,"1A",true);

        Airport airport2 = new Airport(2L, "Schiphol", "1",2,Arrays.asList(airportLineFlight3, airportLineFlight2));

        when(airportRepository.findAll()).thenReturn(Arrays.asList(airport1, airport2));

        // Act
        List<AirportResponse> result = airportService.getAllAirports();

        // Assert
        assertEquals(2L, result.size());

        verify(airportRepository, times(1)).findAll();
    }

    @Test
    public void assignGateToFlight_Success() {
        Airport airport = new Airport(1L, "Airport1", "REQ123", 10, Collections.singletonList(
                new AirportLineFlight(1L, "FL123", "Brussels", 5, null, false)
        ));

        Mockito.when(airportRepository.findById(1L)).thenReturn(java.util.Optional.of(airport));
        Mockito.when(airportRepository.save(Mockito.any())).thenReturn(airport);

        AirportLineFlightDto flightDto = new AirportLineFlightDto(1L, "FL123", "Destination1", 5, "Gate1");
        assertTrue(airportService.assignGateToFlight(flightDto));
    }

    @Test
    public void assignGateToFlight_Failure_AlreadyAssigned() {
        // Mock data
        AirportLineFlightDto flightDto = new AirportLineFlightDto(1L, "FL123", "Destination1", 5, "Gate2");
        AirportLineFlight existingFlight = new AirportLineFlight(1L, "FL123", "Destination1", 5, "Gate1", true);
        Airport airport = new Airport(1L, "Airport1", "REQ123", 10, Collections.singletonList(existingFlight));

        // Mock repository behavior
        Mockito.when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        // Execute the method
        boolean result = airportService.assignGateToFlight(flightDto);

        // Verify interactions and assertions
        Mockito.verify(airportRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(airportRepository);

        assertFalse(result, "The gate assignment should fail.");
    }
}