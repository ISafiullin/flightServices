package fact.it.airportservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "airportlineflight")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirportLineFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private String destination;
    private Integer availableTickets;
    private String gateNumber;
    private boolean assignedGate;
}