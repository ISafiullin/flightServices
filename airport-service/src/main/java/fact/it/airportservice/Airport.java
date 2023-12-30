package fact.it.airportservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String flightRequestNumber;
    private Integer bookedFlights;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AirportLineFlight> airportLineFlightList;
}
