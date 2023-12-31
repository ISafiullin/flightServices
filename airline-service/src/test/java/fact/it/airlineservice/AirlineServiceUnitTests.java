package fact.it.airlineservice;

import fact.it.airlineservice.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

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
        AirlineFlight airlineFlight1 = new AirlineFlight(1, "TUI", "ABC 123",true);
        AirlineFlight airlineFlight2 = new AirlineFlight(2, "Lufthansa", "XYZ 789",false);
        List<AirlineFlight> airlineFlights = Arrays.asList(airlineFlight1, airlineFlight2);

        when(airlineRepository.findByFlightNumberIn(flightNumbers)).thenReturn(airlineFlights);

        // Act
        List<AirlineResponse> airlineResponses = airlineService.available(flightNumbers);

        // Assert
        assertEquals(2, airlineResponses.size());
        assertEquals("ABC 123", airlineResponses.get(0).getFlightNumber());
        assertEquals(true,airlineResponses.get(0).isAvailable());
        assertEquals("XYZ 789", airlineResponses.get(1).getFlightNumber());
        assertEquals(false,airlineResponses.get(1).isAvailable());

        verify(airlineRepository, times(1)).findByFlightNumberIn(flightNumbers);
    }
}
