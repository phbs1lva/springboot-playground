package com.pedro.silva.springboot_playground.module.auth.mapper;

import com.pedro.silva.springboot_playground.module.auth.dto.SignUpRequestDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignUpResponseDTO;
import com.pedro.silva.springboot_playground.module.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SignUpMapper {
    SignUpResponseDTO toResponse(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(SignUpRequestDTO signUpRequestDTO);
}
