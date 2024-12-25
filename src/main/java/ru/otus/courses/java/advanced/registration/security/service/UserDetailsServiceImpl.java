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

import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final UserInfoMapper mapper;
    private final UserInfoRepository userInfoRepository;

    private final Map<String, UserDetails> userDetailsCache = new WeakHashMap<>();

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        if (userDetailsCache.containsKey(username)) {
            return Mono.just(userDetailsCache.get(username));
        }

        userInfoRepository.findByUsername(username)
            .filter(Objects::nonNull)
            .map(mapper::toDetails)
            .subscribe(
                (userInfo) -> userDetailsCache.put(username, userInfo),
                (e) -> {
                    log.error("Happened an error, message" +  e.getMessage(), e);
                    throw new UsernameNotFoundException(username);
                }
            );

        return Mono.just(userDetailsCache.get(username));
    }
}