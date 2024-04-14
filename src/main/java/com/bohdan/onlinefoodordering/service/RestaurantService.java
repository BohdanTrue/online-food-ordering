package com.bohdan.onlinefoodordering.service;

import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantDto;
import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantRequestDto;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.model.User;

import java.util.List;

public interface RestaurantService {
    Restaurant create(RestaurantRequestDto requestDto, Long userId);

    Restaurant update(Long restaurantId, RestaurantRequestDto requestDto);

    void delete(Long restaurantId);

    List<Restaurant> getAll();

    List<Restaurant> search(String name, String cuisineType);

    Restaurant findById(Long restaurantId);

    Restaurant getByUserId(Long userId);

    RestaurantDto addToFavorites(Long restaurantId, Long userId);

    Restaurant updateStatus(Long restaurantId);
}
