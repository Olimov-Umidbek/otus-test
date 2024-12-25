package ru.otus.courses.java.advanced.registration.model.dto;

import java.time.ZonedDateTime;

public record UserInfoDto(
    String username,
    String email,
    ZonedDateTime created,
    ZonedDateTime updated
){

}
