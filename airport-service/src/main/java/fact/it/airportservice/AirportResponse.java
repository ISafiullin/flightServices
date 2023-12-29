package fact.it.airportservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportResponse {
    private String flightRequestNumber;
    private List<AirportLineFlightDto> airportLineItemDtoList;
}
