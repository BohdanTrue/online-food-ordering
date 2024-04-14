package com.bohdan.onlinefoodordering.dto.restaurant;

import com.bohdan.onlinefoodordering.dto.address.AddressResponseDto;
import com.bohdan.onlinefoodordering.dto.order.OrderResponseDto;
import com.bohdan.onlinefoodordering.dto.user.UserResponseDto;
import com.bohdan.onlinefoodordering.model.ContactInformation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RestaurantResponseDto {
    private UserResponseDto owner;
    private String name;
    private String description;
    private String cuisineType;
    private AddressResponseDto address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<OrderResponseDto> orders;
    private List<String> images;
    private LocalDateTime registrationDate;
    private boolean open;
}
