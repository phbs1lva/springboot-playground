package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.auth.dto.*;
import com.pedro.silva.springboot_playground.module.auth.mapper.SignUpMapper;
import com.pedro.silva.springboot_playground.module.user.User;
import com.pedro.silva.springboot_playground.module.user.UserRepository;
import com.pedro.silva.springboot_playground.module.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RestClient restClient;

    private final UserRepository userRepository;

    private final SignUpMapper signUpMapper;

    private static MultiValueMap<String, String> buildJwtTokenMultiValueMap(String clientId, String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("client_secret", "Dy4M64G3GmYmKbBN8GkT4sUA9TlbNNKu");
        map.add("username", username);
        map.add("password", password);

        return map;
    }

    private String getAdminToken() {
        MultiValueMap<String, String> formData = buildJwtTokenMultiValueMap("admin-cli", "admin", "admin");

        KeycloakTokenSuccessResponse response = restClient.post()
                .uri("http://localhost:7777/realms/master/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(KeycloakTokenSuccessResponse.class);

        return response != null ? response.getAccess_token() : null;
    }

    public String createUser(SignUpRequest signUpRequest) {
        String token = getAdminToken();

        KeycloakCredential credential = new KeycloakCredential("password", signUpRequest.getPassword(), false);
        KeycloakUserRequest keycloakUserRequest = new KeycloakUserRequest(
               signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               signUpRequest.getFirstName(),
               signUpRequest.getLastName(),
               true,
               true,
                List.of(credential)
        );

        var response = restClient.post()
                .uri("http://localhost:7777/admin/realms/spring-playground/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(keycloakUserRequest)
                .retrieve()
                .toBodilessEntity();

        String location = Objects.requireNonNull(response.getHeaders().getLocation()).toString();

        return location.substring(location.lastIndexOf('/') + 1);
    }

    public User signup(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException("User already exists.");
        }

        String response = createUser(signUpRequest);

        User newUser = signUpMapper.toEntity(signUpRequest);

        return userRepository.save(newUser);
    }

    public KeycloakTokenSuccessResponse authenticate(SignInRequest signInRequest) {
        MultiValueMap<String, String> payload = buildJwtTokenMultiValueMap("spring-playground", signInRequest.getUsername(), signInRequest.getPassword());

        return restClient
                .post()
                .uri("http://localhost:7777/realms/spring-playground/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(payload)
                .retrieve()
                .body(KeycloakTokenSuccessResponse.class);

    }
}
