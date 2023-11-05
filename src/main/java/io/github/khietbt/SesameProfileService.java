package io.github.khietbt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@EnableR2dbcAuditing
@Slf4j
@SpringBootApplication
public class SesameProfileService implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SesameProfileService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}