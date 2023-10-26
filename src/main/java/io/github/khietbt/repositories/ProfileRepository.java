package io.github.khietbt.repositories;

import io.github.khietbt.entities.Profile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface ProfileRepository extends R2dbcRepository<Profile, UUID> {

}
