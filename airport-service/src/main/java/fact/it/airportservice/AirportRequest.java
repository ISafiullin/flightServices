package fact.it.airportservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportRequest {
    private List<AirportLineFlightDto> airportLineFlightDtoList;
}
