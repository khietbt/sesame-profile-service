package io.github.khietbt.managers;

import io.github.khietbt.annotations.ProtectedResource;
import io.github.khietbt.authorizors.Authorizor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SesameReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final RequestMappingHandlerMapping handlers;

    private final Authorizor authorizor;

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
                            var jwt = ((JwtAuthenticationToken) data.getT1()).getToken().getTokenValue();
                            var handler = (HandlerMethod) data.getT2();
                            var protectedResource = handler.getBeanType().getAnnotation(ProtectedResource.class);
                            var resource = "no-resource";
                            var scope = handler.getMethod().getName();

                            if (protectedResource != null) {
                                resource = protectedResource.value();
                            }

                            return new AuthorizationDecision(authorizor.authorize(jwt, resource, scope));
                        }
                );
    }

}
