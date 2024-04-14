package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.dto.food.FoodRequestDto;
import com.bohdan.onlinefoodordering.mapper.FoodMapper;
import com.bohdan.onlinefoodordering.model.Food;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.service.FoodService;
import com.bohdan.onlinefoodordering.service.RestaurantService;
import com.bohdan.onlinefoodordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/foods")
public class AdminFoodController {
    private final FoodService foodService;
    private final RestaurantService restaurantService;
    private final FoodMapper foodMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Food> create(@RequestBody FoodRequestDto requestDto) {
        Restaurant restaurant = restaurantService.findById(requestDto.getRestaurantId());
        Food food = foodService.create(requestDto, requestDto.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Food> delete(@PathVariable Long id) {
        foodService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long id) {
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
