package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.auth.dto.KeycloakTokenErrorResponseDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.KeycloakTokenSuccessResponseDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignInRequestDTO;
import com.pedro.silva.springboot_playground.module.auth.exception.UserNotFoundException;
import com.pedro.silva.springboot_playground.module.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private static MultiValueMap<String, String> buildJwtTokenMultiValueMap(SignInRequestDTO signInRequestDTO) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", "password");
        map.add("client_id", "spring-playground");
        map.add("client_secret", "yL7x92oWAqpOKR0vOONMN4SJ0rPEtxqi");
        map.add("username", signInRequestDTO.getUsername());
        map.add("password", signInRequestDTO.getPassword());

        return map;
    }

    public Mono<KeycloakTokenSuccessResponseDTO> authenticate(SignInRequestDTO signInRequestDTO) {
        WebClient webClient = WebClient.create("http://localhost:7777/realms/spring-playground/protocol/openid-connect/token");

        MultiValueMap<String, String> payload = buildJwtTokenMultiValueMap(signInRequestDTO);

        return webClient
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(payload))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response
                                .bodyToMono(KeycloakTokenErrorResponseDTO.class)
                                .flatMap(dto -> {
                                    return Mono.error(new UserNotFoundException(
                                            HttpStatus.UNAUTHORIZED.getReasonPhrase()
                                    ));
                                }))
                .bodyToMono(KeycloakTokenSuccessResponseDTO.class);
    }
}
