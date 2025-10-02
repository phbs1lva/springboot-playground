package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.auth.dto.SignInResponseDTO;
import com.pedro.silva.springboot_playground.module.user.User;
import com.pedro.silva.springboot_playground.module.user.dto.UserSignInDTO;
import com.pedro.silva.springboot_playground.module.user.dto.UserSignUpDTO;
import com.pedro.silva.springboot_playground.shared.JWTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JWTService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid UserSignUpDTO userSignUpDTO) {
        User registeredUser = authenticationService.signup(userSignUpDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signin(@Valid UserSignInDTO userSignInDTO) {
        User authenticatedUser = authenticationService.authenticate(userSignInDTO);

       String jwtToken = jwtService.generateToken(authenticatedUser);

       SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
       signInResponseDTO.setToken(jwtToken);
       signInResponseDTO.setExpiresIn(jwtService.getJwtExpiration());

       return ResponseEntity.ok(signInResponseDTO);
    }
}
