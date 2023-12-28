package fact.it.airlineservice;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineService {

    final private AirlineRepository airlineRepository;
    @PostConstruct
    public void loadData() {
        if(airlineRepository.count() <= 0){
            AirlineFlight airlineFlight = new AirlineFlight();
            airlineFlight.setName("TUI");
            airlineFlight.setFlightNumber("AA123");

            AirlineFlight airlineFlight1 = new AirlineFlight();
            airlineFlight1.setName("Brussels Airlines");
            airlineFlight1.setFlightNumber("DK545");

            AirlineFlight airlineFlight2 = new AirlineFlight();
            airlineFlight2.setName("RyanAir");
            airlineFlight2.setFlightNumber("FF112");

            airlineRepository.save(airlineFlight);
            airlineRepository.save(airlineFlight1);
            airlineRepository.save(airlineFlight2);
        }
    }

    @Transactional(readOnly = true)
    public List<AirlineResponse> available(List<String> flightNumber) {

        return airlineRepository.findByFlightNumberIn(flightNumber).stream()
                .map(airlineFlight ->
                        AirlineResponse.builder()
                                .flightNumber(airlineFlight.getFlightNumber())
                                .available(true)
                                .build()
                ).toList();
    }
}
