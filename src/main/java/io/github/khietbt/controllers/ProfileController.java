package io.github.khietbt.controllers;

import io.github.khietbt.annotations.KeycloakResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@KeycloakResource("resource:sesame:sesame-profile-service:profile")
@RequestMapping("/profiles")
@RestController
@Slf4j
public class ProfileController {
    @GetMapping
    public Flux<Integer> getList() {
        return Flux.range(0, 10).delayElements(Duration.ofMillis(100));
    }
}
