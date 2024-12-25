package ru.otus.courses.java.advanced.registration.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.otus.courses.java.advanced.registration.model.dto.UserRegistrationDto;
import ru.otus.courses.java.advanced.registration.service.UserInfoService;

import java.util.stream.IntStream;

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

        IntStream.rangeClosed(1, usersCount)
                .parallel()
                .mapToObj(i -> new UserRegistrationDto("user%d".formatted(i), "pass%d".formatted(i)))
                .forEach(userInfoService::createUser);

        log.info("Users createdAt");
    }
}
