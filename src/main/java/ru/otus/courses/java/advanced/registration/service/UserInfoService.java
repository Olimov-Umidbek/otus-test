package ru.otus.courses.java.advanced.registration.service;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.courses.java.advanced.registration.model.dto.UserInfoDto;
import ru.otus.courses.java.advanced.registration.model.dto.UserRegistrationDto;

public interface UserInfoService {
    Mono<UserInfoDto> getCurrentUser();

    Mono<UserInfoDto> createUser(@Valid UserRegistrationDto userRegistrationDto);

    Flux<UserInfoDto> getUsers();

    Flux<String> getUserNames();

    Flux<String> getEmailAddresses();
}
