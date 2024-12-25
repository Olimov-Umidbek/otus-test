package ru.otus.courses.java.advanced.registration.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.otus.courses.java.advanced.registration.model.domain.UserRegistration;
import ru.otus.courses.java.advanced.registration.service.UserInfoService;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    @Value("${users.count:10}")
    public int usersCount;

    private final UserInfoService userInfoService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Users count to create: {}", usersCount);

        Flux<UserRegistration> flux = Flux.range(1, usersCount)
            .map(i -> new UserRegistration("user%d".formatted(i), "pass%d".formatted(i)))
            .subscribeOn(Schedulers.boundedElastic());

        flux
            .doOnTerminate(() -> log.info("All users created"))
            .doOnError(e -> log.error("Error occurred while creating users: ", e))
            .subscribe(userInfoService::createUser);

        userInfoService.createUser(new UserRegistration("admin", "admin"));
    }
}
