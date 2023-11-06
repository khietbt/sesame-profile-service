package io.github.khietbt.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RequestMapping("/profiles")
@RestController
@Slf4j
public class ProfileController {
    @GetMapping
    public Flux<Integer> getList() {
        return Flux.range(0, 10).delayElements(Duration.ofMillis(100));
    }
}
