package com.bohdan.onlinefoodordering.dto.auth;

import com.bohdan.onlinefoodordering.model.UserRole;
import lombok.Data;

@Data
public class AuthResponseDto {
    private String jwt;
    private String message;
    private UserRole role;
}
