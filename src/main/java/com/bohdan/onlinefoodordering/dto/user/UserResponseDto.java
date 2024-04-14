package com.bohdan.onlinefoodordering.dto.user;

import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantDto;
import com.bohdan.onlinefoodordering.model.Address;
import com.bohdan.onlinefoodordering.model.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private String fullName;
    private String email;
    private UserRole role;
    private List<RestaurantDto> favorites;
    private List<Address> addresses;
}
