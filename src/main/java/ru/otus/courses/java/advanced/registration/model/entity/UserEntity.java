package ru.otus.courses.java.advanced.registration.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.otus.courses.java.advanced.registration.model.enums.UserRole;

import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
public record UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id,

    @Column(nullable = false, unique = true, length = 60)
    String username,

    @Column(name = "password", nullable = false)
    String password,

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    UserRole role,

    @Column(name = "email", unique = true)
    String email,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    ZonedDateTime createdAt,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    ZonedDateTime updatedAt
) {  }
