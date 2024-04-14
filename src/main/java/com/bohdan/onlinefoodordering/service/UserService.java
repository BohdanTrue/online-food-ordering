package com.bohdan.onlinefoodordering.service;

import com.bohdan.onlinefoodordering.model.User;

public interface UserService {
//    User findUserByJwtToken(String jwt);
    User findUserByEmail(String email);

    User findUserById(Long userId);
}
