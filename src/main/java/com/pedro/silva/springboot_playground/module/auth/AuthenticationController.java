package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.auth.dto.*;
import com.pedro.silva.springboot_playground.module.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        User user = authenticationService.signup(signUpRequest);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<KeycloakTokenSuccessResponse> signin(@RequestBody @Valid SignInRequest signInRequest) {
        KeycloakTokenSuccessResponse response = authenticationService.authenticate(signInRequest);

        return ResponseEntity.ok(response);
    }
}
