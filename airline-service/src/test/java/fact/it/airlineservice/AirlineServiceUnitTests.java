package fact.it.airlineservice;

import ch.qos.logback.core.joran.conditional.IfAction;
import fact.it.airlineservice.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceUnitTests {

    @InjectMocks
    private AirlineService airlineService;

    @Mock
    private AirlineRepository airlineRepository;

    @Test
    public void TestIfAvailable(){
        // Arrange
        List<String> flightNumbers = Arrays.asList("ABC 123", "XYZ 789");
        AirlineFlight airlineFlight1 = new AirlineFlight(1L, "TUI", "ABC 123","SCHEDULED",true);
        AirlineFlight airlineFlight2 = new AirlineFlight(2L, "Lufthansa", "XYZ 789","CANCELLED",false);
        List<AirlineFlight> airlineFlights = Arrays.asList(airlineFlight1, airlineFlight2);

        when(airlineRepository.findByFlightNumberIn(flightNumbers)).thenReturn(airlineFlights);

        // Act
        List<AirlineResponse> airlineResponses = airlineService.available(flightNumbers);

        // Assert
        assertEquals(2, airlineResponses.size());
        assertEquals("ABC 123", airlineResponses.get(0).getFlightNumber());
        assertEquals(true,airlineResponses.get(0).isAvailable());
        assertEquals("XYZ 789", airlineResponses.get(1).getFlightNumber());
        assertEquals(false, !airlineResponses.get(1).isAvailable());

        verify(airlineRepository, times(1)).findByFlightNumberIn(flightNumbers);
    }

    @Test
    void cancelFlight_Success() {
        // Mocking the behavior of the repository
        AirlineFlight mockFlight = new AirlineFlight(1L, "Flight1", "ABC123","SCHEDULED",true);
        when(airlineRepository.findById(anyLong())).thenReturn(Optional.of(mockFlight));
        when(airlineRepository.save(any(AirlineFlight.class))).thenReturn(mockFlight);

        // Calling the method to cancel the flight
        boolean result = airlineService.cancelFlight(1L);

        // Verifying that the save method was called
        verify(airlineRepository, times(1)).save(any(AirlineFlight.class));

        // Asserting the result
        assertTrue(result);
        // Asserting that the flight status is "CANCELLED"
        assertTrue("CANCELLED".equals(mockFlight.getStatus()));
        // Asserting that the flight is not available
        assertFalse(mockFlight.isAvailable());
    }

    @Test
    void cancelFlight_FlightNotFound() {
        // Mocking the behavior of the repository when the flight is not found
        when(airlineRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Calling the method to cancel the flight
        boolean result = airlineService.cancelFlight(1L);

        // Asserting the result
        assertFalse(result); // Flight not found, so canceling is unsuccessful
    }

    @Test
    void cancelFlight_AlreadyCancelled() {
        // Mocking the behavior of the repository when the flight is already cancelled
        AirlineFlight mockCancelledFlight = new AirlineFlight(1L, "Flight1", "ABC123","CANCELLED" ,false);
        mockCancelledFlight.setStatus("CANCELLED");
        when(airlineRepository.findById(anyLong())).thenReturn(Optional.of(mockCancelledFlight));

        // Calling the method to cancel the flight
        boolean result = airlineService.cancelFlight(1L);

        // Asserting the result
        assertFalse(result); // Flight is already cancelled, so canceling is unsuccessful
    }
}
