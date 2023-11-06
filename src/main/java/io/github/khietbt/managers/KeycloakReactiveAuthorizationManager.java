package io.github.khietbt.managers;

import io.github.khietbt.annotations.ProtectedResource;
import lombok.RequiredArgsConstructor;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
public class KeycloakReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final RequestMappingHandlerMapping handlers;
    private final AuthzClient keycloakClient;

    /**
     * Determines if access is granted for a specific authentication and context.
     *
     * @param authentication the Authentication to check
     * @param context        the context to check
     * @return a decision or empty Mono if no decision could be made.
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        return Mono
                .zip(
                        authentication,
                        handlers.getHandler(context.getExchange())
                )
                .map(
                        data -> {
                            try {
                                var jwt = ((JwtAuthenticationToken) data.getT1()).getToken().getTokenValue();
                                var authorizationRequest = createKeycloakAuthorizationRequest((HandlerMethod) data.getT2());

                                keycloakClient.authorization(jwt).authorize(authorizationRequest);

                                return new AuthorizationDecision(true);
                            } catch (Exception ignored) {
                                return new AuthorizationDecision(false);
                            }
                        }
                );
    }

    private AuthorizationRequest createKeycloakAuthorizationRequest(HandlerMethod handler) {
        var request = new AuthorizationRequest();

        Arrays.stream(handler.getBeanType().getAnnotationsByType(ProtectedResource.class))
                .forEach(r -> request.addPermission(r.value(), handler.getMethod().getName()));

        return request;
    }
}
