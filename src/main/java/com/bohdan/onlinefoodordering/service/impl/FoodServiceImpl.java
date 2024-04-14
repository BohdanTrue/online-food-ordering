package com.bohdan.onlinefoodordering.service.impl;

import com.bohdan.onlinefoodordering.dto.food.FoodRequestDto;
import com.bohdan.onlinefoodordering.mapper.FoodMapper;
import com.bohdan.onlinefoodordering.model.Category;
import com.bohdan.onlinefoodordering.model.Food;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.repository.FoodRepository;
import com.bohdan.onlinefoodordering.service.FoodService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    @Override
    public Food create(FoodRequestDto requestDto, Category category, Restaurant restaurant) {
        Food food = foodMapper.toEntity(requestDto);
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void delete(Long foodId) {
        Food food = findById(foodId);
        food.setRestaurant(null);

        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantsFood(
            Long restaurantId,
            boolean isVegetarian,
            boolean isSeasonal,
            String foodCategory
    ) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }

        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }

        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream()
                .filter(food -> {
                    if (food.getFoodCategory() != null) {
                        return food.getFoodCategory().getName().equals(foodCategory);
                    }
                    return false;
                }).toList();
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream()
                .filter(food -> food.isSeasonal() == isSeasonal)
                .toList();
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream()
                .filter(food -> food.isVegetarian() == isVegetarian)
                .toList();
    }

    @Override
    public List<Food> search(String keyword) {
        System.out.println("keyword: " + keyword);
        System.out.println(foodRepository.search(keyword));
        return foodRepository.search(keyword);
    }

    @Override
    public Food findById(Long foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find food by id: " + foodId));
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) {
        Food food = findById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
