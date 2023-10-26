package io.github.khietbt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @GetMapping
    public Flux<Integer> getAllProfiles() {
        return Flux.range(0, 10).delayElements(Duration.ofSeconds(1));
    }
}
