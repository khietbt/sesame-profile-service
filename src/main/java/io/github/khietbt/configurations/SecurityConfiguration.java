package io.github.khietbt.configurations;

import io.github.khietbt.managers.KeycloakReactiveAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.keycloak.authorization.client.AuthzClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final RequestMappingHandlerMapping handlers;

    private final AuthzClient authzClient;

    @Bean
    public SecurityWebFilterChain securityFilterChain(
            ServerHttpSecurity http
    ) {
        http.authorizeExchange(
                c -> c.pathMatchers("/**")
                        .access(new KeycloakReactiveAuthorizationManager(handlers, authzClient))
                        .anyExchange()
                        .authenticated()
        );
        http.oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
