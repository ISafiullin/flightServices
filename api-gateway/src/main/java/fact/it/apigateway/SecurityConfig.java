package fact.it.apigateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.POST, "/flights/create").permitAll()
                                .pathMatchers(HttpMethod.GET, "/flights").permitAll()
                                .pathMatchers(HttpMethod.DELETE, "/flights/delete/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/airlines").permitAll()
                                .pathMatchers(HttpMethod.PUT, "/airlines/cancelFlight/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/airport").permitAll()
                                .pathMatchers(HttpMethod.POST, "/airport/scheduleFlight/**").permitAll()
                                .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                );
        return serverHttpSecurity.build();
    }
}
