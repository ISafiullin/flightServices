package fact.it.flightservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FlightRepository extends MongoRepository<Flight, String> {

    List<Flight> findByFlightNumberIn(List<String> flightNumber);

}