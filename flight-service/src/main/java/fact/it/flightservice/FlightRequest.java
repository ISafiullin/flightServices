package fact.it.flightservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequest {

    private String flightNumber;
    private String destination;
    private Integer availableTickets;
}
