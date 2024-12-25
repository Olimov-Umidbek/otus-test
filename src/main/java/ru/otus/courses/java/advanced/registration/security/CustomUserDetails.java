package ru.otus.courses.java.advanced.registration.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.courses.java.advanced.registration.model.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(UserEntity userEntity) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userEntity.role().name()));
    }

    @Override
    public String getPassword() {
        return userEntity.password();
    }

    @Override
    public String getUsername() {
        return userEntity.username();
    }
}
