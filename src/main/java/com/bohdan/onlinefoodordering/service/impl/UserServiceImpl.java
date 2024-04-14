package com.bohdan.onlinefoodordering.service.impl;

import com.bohdan.onlinefoodordering.config.JwtProvider;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.repository.UserRepository;
import com.bohdan.onlinefoodordering.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
//    @Override
//    public User findUserByJwtToken(String jwt) {
//        String email = jwtProvider.getEmailFromJwtToken(jwt);
//
//        return findUserByEmail(email);
//    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new Exception("User with this email: " + email + " already exist"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user by id: " + userId));
    }
}
