package fact.it.airlineservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "airlineflight")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirlineFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String flightNumber;
    private String status;
    private boolean available;
}
