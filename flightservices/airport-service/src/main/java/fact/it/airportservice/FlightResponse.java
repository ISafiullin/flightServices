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
    private String id;
    private String flightNumber;
    private String bookedFlights;
    private String name;
    private String country;
    private Integer iata;
}
