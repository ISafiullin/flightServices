package fact.it.flightservice;

public record FlightRegistrationRequest(
        String destination,
        Integer availableTickets) {

}
