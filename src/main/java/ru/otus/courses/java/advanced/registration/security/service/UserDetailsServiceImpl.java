package ru.otus.courses.java.advanced.registration.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.courses.java.advanced.registration.mapper.UserInfoMapper;
import ru.otus.courses.java.advanced.registration.repository.UserInfoRepository;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final UserInfoMapper mapper;
    private final UserInfoRepository userInfoRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return userInfoRepository.findByUsername(username)
            .filter(Objects::nonNull)
            .log()
            .map(mapper::toDetails)
            .doOnError(e -> {
                    log.error("Happened an error, message" +  e.getMessage(), e);
                    throw new UsernameNotFoundException(username);
                }
            )
            .switchIfEmpty(Mono.defer(() -> {
                log.error("User not found: " + username);
                return Mono.error(new UsernameNotFoundException(username));
            }));
    }
}