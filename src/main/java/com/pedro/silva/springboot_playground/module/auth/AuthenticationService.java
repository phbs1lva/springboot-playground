package com.pedro.silva.springboot_playground.module.auth;

import com.pedro.silva.springboot_playground.module.auth.dto.SignInRequestDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignUpRequestDTO;
import com.pedro.silva.springboot_playground.module.auth.dto.SignUpResponseDTO;
import com.pedro.silva.springboot_playground.module.auth.mapper.SignUpMapper;
import com.pedro.silva.springboot_playground.module.user.User;
import com.pedro.silva.springboot_playground.module.user.UserRepository;
import com.pedro.silva.springboot_playground.module.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final SignUpMapper signUpMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public SignUpResponseDTO signup(SignUpRequestDTO signUpRequestDTO) {
        if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        User newUser = new User();

        newUser.setUsername(signUpRequestDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));

        User createdUser = userRepository.save(newUser);

        return signUpMapper.toResponse(createdUser);
    }

    public User authenticate(SignInRequestDTO signInRequestDTO) {
        String username = signInRequestDTO.getUsername();
        String password = signInRequestDTO.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return userRepository.findByUsername(username)
                             .orElseThrow();
    }
}
