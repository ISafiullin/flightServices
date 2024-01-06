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
                        exchange.pathMatchers(HttpMethod.GET, "/api/**").permitAll() // Allow GET requests to /api/**
                        .pathMatchers(HttpMethod.POST, "/api/**").permitAll() // Allow POST requests to /api/**
                        .pathMatchers(HttpMethod.PUT, "/api/**").permitAll() // Allow PUT requests to /api/**
                        .pathMatchers(HttpMethod.DELETE, "/api/**").permitAll() // Allow DELETE requests to /api/**
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                );
        return serverHttpSecurity.build();
    }
}
