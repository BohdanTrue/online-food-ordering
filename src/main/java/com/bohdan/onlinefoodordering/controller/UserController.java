package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.dto.user.UserResponseDto;
import com.bohdan.onlinefoodordering.mapper.UserMapper;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public UserResponseDto findUserByJwtToken(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        return userMapper.toDto(user);
    }
}
