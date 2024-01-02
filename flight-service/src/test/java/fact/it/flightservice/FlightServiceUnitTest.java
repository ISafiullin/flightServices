package fact.it.flightservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceUnitTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Test
    public void testCreateFlight() {
        // Arrange
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setFlightNumber("ABC 123");
        flightRequest.setDestination("Brussels");
        flightRequest.setAvailableTickets(430);

        // Act
        flightService.createFlight(flightRequest);

        // Assert
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    public void testGetAllFlights() {
        // Arrange
        Flight flight = new Flight();
        flight.setId("1");
        flight.setFlightNumber("ABC 123");
        flight.setDestination("Brussels");
        flight.setAvailableTickets(430);

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight));

        // Act
        List<FlightResponse> flights = flightService.getAllFlights();

        // Assert
        assertEquals(1, flights.size());
        assertEquals("ABC 123", flights.get(0).getFlightNumber());
        assertEquals("Brussels", flights.get(0).getDestination());
        assertEquals(430, flights.get(0).getAvailableTickets());

        verify(flightRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllFlightsByDestination() {
        // Arrange
        Flight flight = new Flight();
        flight.setId("1");
        flight.setFlightNumber("ABC 123");
        flight.setDestination("Brussels");
        flight.setAvailableTickets(430);

        when(flightRepository.findByFlightNumberIn(Arrays.asList("ABC 123"))).thenReturn(Arrays.asList(flight));

        // Act
        List<FlightResponse> flights = flightService.getAllFlightsByFlightNumber(Arrays.asList("ABC 123"));

        // Assert
        assertEquals(1, flights.size());
        assertEquals("1", flights.get(0).getId());
        assertEquals("Brussels", flights.get(0).getDestination());
        assertEquals("ABC 123", flights.get(0).getFlightNumber());
        assertEquals(430, flights.get(0).getAvailableTickets());

        verify(flightRepository, times(1)).findByFlightNumberIn(Arrays.asList(flight.getFlightNumber()));
    }

    @Test
    public void testDeleteFlight() {
        // Mocking data
        String id = "1";

        // Perform the delete operation
        flightService.deleteFlight(id);

        Mockito.lenient().when(flightRepository.findById(Mockito.eq(id))).thenReturn(Optional.of(buildSampleFlight()));

    }

    private Flight buildSampleFlight() {
        return Flight.builder()
                .id("1")
                .flightNumber("AA123")
                .destination("New York")
                .availableTickets(484)
                .build();
    }
}