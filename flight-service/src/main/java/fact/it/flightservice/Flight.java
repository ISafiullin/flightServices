package fact.it.flightservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "flight")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Flight {

    private Integer id;
    private String flightNumber;
    private String destination;
    private Integer availableTickets;
}
