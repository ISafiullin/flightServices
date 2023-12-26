package fact.it.airline;

public record AirlineRegistrationRequest(
        String name,
        String phoneNumber,
        String email) {
}
