package fact.it.airline;

import org.springframework.stereotype.Service;

@Service
public record AirlineService() {
    public void registerAirline(AirlineRegistrationRequest request){
        Airline airline = Airline.builder()
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .build();
    }
}
