package com.pedro.silva.springboot_playground.module.user;

import com.pedro.silva.springboot_playground.module.user.dto.UserSignUpRequest;
import com.pedro.silva.springboot_playground.module.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUser(UUID uuid) {
        return userRepository.findById(uuid);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User signup(UserSignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already taken");
        }

        User newUser = new User();

        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        // TODO: Encrypt password
        newUser.setPassword(request.getPassword());

       return userRepository.save(newUser);
    }
}
