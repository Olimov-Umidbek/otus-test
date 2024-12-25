package ru.otus.courses.java.advanced.registration.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.courses.java.advanced.registration.mapper.UserInfoMapper;
import ru.otus.courses.java.advanced.registration.model.dto.UserInfoDto;
import ru.otus.courses.java.advanced.registration.model.domain.UserRegistration;
import ru.otus.courses.java.advanced.registration.model.entity.UserEntity;
import ru.otus.courses.java.advanced.registration.model.enums.UserRole;
import ru.otus.courses.java.advanced.registration.repository.UserInfoRepository;
import ru.otus.courses.java.advanced.registration.service.UserInfoService;

import java.security.Principal;
import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoMapper mapper;

    @Override
    public Mono<UserInfoDto> getCurrentUser() {
        Principal currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userInfoRepository.findByUsername(currentUser.getName())
            .map(mapper::toDto)
            .switchIfEmpty(Mono.error(new UsernameNotFoundException(currentUser.getName())));
    }

    @Override
    public Mono<UserInfoDto> createUser(UserRegistration userRegistration) {
        try {
            UserEntity userEntity = new UserEntity(
                null,
                userRegistration.username(),
                passwordEncoder.encode(userRegistration.password()),
                UserRole.USER,
                "test@test.com",
                ZonedDateTime.now(),
                ZonedDateTime.now()

            );

            return userInfoRepository.save(userEntity)
                .map(mapper::toDto);
        }catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public Flux<UserInfoDto> getUsers() {
        return userInfoRepository.findAll().map(mapper::toDto);
    }

    @Override
    public Flux<String> getUserNames() {
        return userInfoRepository.getUsernames();
    }

    @Override
    public Flux<String> getEmailAddresses() {
        return userInfoRepository.getEmails();
    }
}
