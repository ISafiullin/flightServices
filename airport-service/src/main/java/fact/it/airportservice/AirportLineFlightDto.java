package fact.it.airportservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportLineFlightDto {
    private Long id;
    private String flightNumber;
    private String destination;
    private Integer availableTickets;
    private String gateNumber;

}
