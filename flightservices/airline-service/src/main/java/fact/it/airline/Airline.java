package fact.it.airline;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Airline {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;
}
