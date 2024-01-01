package fact.it.flightservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceUnitTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Test
    public void testCreateProduct() {
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
        flight.setId(1L);
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
        flight.setId(1L);
        flight.setFlightNumber("ABC 123");
        flight.setDestination("Brussels");
        flight.setAvailableTickets(430);

        when(flightRepository.findByDestinationIn(Arrays.asList("Brussels"))).thenReturn(Arrays.asList(flight));

        // Act
        List<FlightResponse> flights = flightService.getAllFlightsByDestination(Arrays.asList("Brussels"));

        // Assert
        assertEquals(1, flights.size());
        assertEquals(1L, flights.get(0).getId());
        assertEquals("Brussels", flights.get(0).getDestination());
        assertEquals("ABC 123", flights.get(0).getFlightNumber());
        assertEquals(430, flights.get(0).getAvailableTickets());

        verify(flightRepository, times(1)).findByDestinationIn(Arrays.asList(flight.getDestination()));
    }
}