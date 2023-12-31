package fact.it.airportservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private String destination;
    private Integer availableTickets;
    private String gateNumber;
    private boolean assignedGate;
}
