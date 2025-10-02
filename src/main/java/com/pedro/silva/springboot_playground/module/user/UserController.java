package com.pedro.silva.springboot_playground.module.user;

import com.pedro.silva.springboot_playground.module.user.dto.UserSignUpRequest;
import com.pedro.silva.springboot_playground.module.user.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signupUser(@Valid UserSignUpRequest request) {
        try {
            User newUser = userService.signup(request);
            URI location = URI.create("/api/users" + newUser.getId());
            return ResponseEntity.created(location).body(newUser);
        } catch (UserAlreadyExistsException e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
