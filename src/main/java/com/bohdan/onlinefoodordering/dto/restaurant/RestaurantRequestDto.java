package com.bohdan.onlinefoodordering.dto.restaurant;

import com.bohdan.onlinefoodordering.model.Address;
import com.bohdan.onlinefoodordering.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantRequestDto {
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
