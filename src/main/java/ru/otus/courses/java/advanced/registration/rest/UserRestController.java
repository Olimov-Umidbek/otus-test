package ru.otus.courses.java.advanced.registration.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.courses.java.advanced.registration.model.dto.UserInfoDto;
import ru.otus.courses.java.advanced.registration.model.domain.UserRegistration;
import ru.otus.courses.java.advanced.registration.service.UserInfoService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserInfoService userInfoService;


    @GetMapping("/current")
    public Mono<UserInfoDto> getCurrentUser() {
        return userInfoService.getCurrentUser();
    }

    @PostMapping
    public Mono<UserInfoDto> createUser(@RequestBody @Valid UserRegistration userRegistration) {
        return userInfoService.createUser(userRegistration);
    }

    @GetMapping
    public Flux<UserInfoDto> getUsers() {
        return userInfoService.getUsers();
    }

    @GetMapping("/names")
    public Flux<String> getUserNames() {
        return userInfoService.getUserNames();
    }

    @GetMapping("/emails")
    public Flux<String> getEmails() {
        return userInfoService.getEmailAddresses();
    }
}
