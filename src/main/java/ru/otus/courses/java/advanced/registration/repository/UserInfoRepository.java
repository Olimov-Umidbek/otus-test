package ru.otus.courses.java.advanced.registration.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.courses.java.advanced.registration.model.entity.UserEntity;

import java.util.UUID;

@Repository
public interface UserInfoRepository extends R2dbcRepository<UserEntity, UUID> {

    @Query("SELECT * FROM users where username = :username")
    Mono<UserEntity> findByUsername(String username);

    @Query("SELECT username FROM users")
    Flux<String> getUsernames();

    @Query("SELECT email FROM users WHERE email IS NOT NULL")
    Flux<String> getEmails();
}
