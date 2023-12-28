package fact.it.airportservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportLineFlightDto {
    private Integer id;
    private String flightNumber;
    private String destination;
    private Integer availableTickets;
    private Integer bookedFlights;

}
