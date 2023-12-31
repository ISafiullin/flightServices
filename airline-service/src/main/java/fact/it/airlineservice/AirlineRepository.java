package fact.it.airlineservice;

import fact.it.airlineservice.AirlineFlight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AirlineRepository extends  JpaRepository<AirlineFlight, Long> {
    List<AirlineFlight> findByFlightNumberIn(List<String>flightNumber);
}
