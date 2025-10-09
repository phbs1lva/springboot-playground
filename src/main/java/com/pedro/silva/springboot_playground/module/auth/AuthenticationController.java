package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.auth.dto.KeycloakTokenSuccessResponseDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignInRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public Mono<ResponseEntity<KeycloakTokenSuccessResponseDTO>> signin(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        Mono<KeycloakTokenSuccessResponseDTO> response = authenticationService.authenticate(signInRequestDTO);

        return response.map(ResponseEntity::ok);
    }
}
