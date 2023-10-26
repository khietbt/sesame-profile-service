package io.github.khietbt.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public ReactiveAuditorAware<String> auditorAware() {
        return () -> Mono.just("admin");
    }
}
