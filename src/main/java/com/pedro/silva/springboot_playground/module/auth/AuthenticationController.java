package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.auth.dto.SignInRequestDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignInResponseDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignUpRequestDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignUpResponseDTO;
import com.pedro.silva.springboot_playground.module.user.User;
import com.pedro.silva.springboot_playground.shared.JWTService;
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
    private final JWTService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signup(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        SignUpResponseDTO registeredUser = authenticationService.signup(signUpRequestDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signin(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        User authenticatedUser = authenticationService.authenticate(signInRequestDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        signInResponseDTO.setToken(jwtToken);
        signInResponseDTO.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(signInResponseDTO);
    }
}
