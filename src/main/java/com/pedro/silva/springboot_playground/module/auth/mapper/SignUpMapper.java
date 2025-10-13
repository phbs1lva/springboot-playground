package com.pedro.silva.springboot_playground.module.auth.mapper;

import com.pedro.silva.springboot_playground.module.auth.dto.KeycloakUserResponse;
import com.pedro.silva.springboot_playground.module.auth.dto.SignUpRequest;
import com.pedro.silva.springboot_playground.module.user.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SignUpMapper {
    KeycloakUserResponse toResponse(User user);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "username")
    User toEntity(SignUpRequest signUpRequest);
}
