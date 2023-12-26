package fact.it.flightservice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flight {

    private Integer id;
    private Integer flightNumber;
    private String destination;
    private Integer availableTickets;
}
