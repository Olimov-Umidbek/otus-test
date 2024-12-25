package ru.otus.courses.java.advanced.registration.mapper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.otus.courses.java.advanced.registration.model.dto.UserInfoDto;
import ru.otus.courses.java.advanced.registration.model.entity.UserEntity;
import ru.otus.courses.java.advanced.registration.security.CustomUserDetails;

@Component
public class UserInfoMapper {

    public UserInfoDto toDto(UserEntity userEntity) {
        return new UserInfoDto(
            userEntity.username(),
            userEntity.email(),
            userEntity.createdAt(),
            userEntity.updatedAt()
        );
    }

    public UserDetails toDetails(UserEntity userEntity) {
        return new CustomUserDetails(userEntity);
    }
}
