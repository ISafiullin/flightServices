package fact.it.airportservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportResponse {
    private String name;
    private String flightRequestNumber;
    private Integer bookedFlights;
    private List<AirportLineFlightDto> airportLineItemDtoList;
}
