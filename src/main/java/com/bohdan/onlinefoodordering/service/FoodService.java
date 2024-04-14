package com.bohdan.onlinefoodordering.service;

import com.bohdan.onlinefoodordering.dto.food.FoodRequestDto;
import com.bohdan.onlinefoodordering.model.Category;
import com.bohdan.onlinefoodordering.model.Food;
import com.bohdan.onlinefoodordering.model.Restaurant;

import java.util.List;

public interface FoodService {
    Food create(FoodRequestDto requestDto, Category category, Restaurant restaurant);

    void delete(Long foodId);

    List<Food> getRestaurantsFood(
            Long restaurantId,
            boolean isVegetarian,
            boolean isSeasonal,
            String foodCategory
    );

    List<Food> search(String keyword);

    Food findById(Long foodId);

    Food updateAvailabilityStatus(Long foodId);
}
