package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.mapper.FoodMapper;
import com.bohdan.onlinefoodordering.model.Food;
import com.bohdan.onlinefoodordering.service.FoodService;
import com.bohdan.onlinefoodordering.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name) {
        List<Food> foods = foodService.search(name);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @PathVariable Long restaurantId,
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam(required = false) String food_category
    ) {
        List<Food> foods = foodService.getRestaurantsFood(
                restaurantId,
                vegetarian,
                seasonal,
                food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
