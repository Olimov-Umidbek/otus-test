package ru.otus.courses.java.advanced.registration.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
