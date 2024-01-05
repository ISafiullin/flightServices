package fact.it.airportservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<AirportLineFlight> findByFlightNumberIn(List<String> flightNumbers);
}
