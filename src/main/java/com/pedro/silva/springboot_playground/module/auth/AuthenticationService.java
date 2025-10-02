package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.user.User;
import com.pedro.silva.springboot_playground.module.user.UserRepository;
import com.pedro.silva.springboot_playground.module.user.dto.UserSignInDTO;
import com.pedro.silva.springboot_playground.module.user.dto.UserSignUpDTO;
import com.pedro.silva.springboot_playground.module.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public User signup(UserSignUpDTO userSignUpDTO) {
        if (userRepository.existsByEmail(userSignUpDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email is already taken");
        }

        User newUser = new User();

        newUser.setEmail(userSignUpDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword()));

        return userRepository.save(newUser);
    }

    public User authenticate(UserSignInDTO userSignInDTO) {
        String email = userSignInDTO.getEmail();
        String password = userSignInDTO.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return userRepository.findByEmail(email)
                             .orElseThrow();
    }
}
