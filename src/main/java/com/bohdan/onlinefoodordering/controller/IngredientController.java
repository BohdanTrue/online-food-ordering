package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.dto.ingredient.IngredientCategoryRequestDto;
import com.bohdan.onlinefoodordering.dto.ingredient.IngredientItemRequestDto;
import com.bohdan.onlinefoodordering.model.IngredientCategory;
import com.bohdan.onlinefoodordering.model.IngredientItem;
import com.bohdan.onlinefoodordering.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequestDto requestDto) {
        IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(
                requestDto.getName(),
                requestDto.getRestaurantId()
        );

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<IngredientItem> createIngredientItem(@RequestBody IngredientItemRequestDto requestDto) {
        IngredientItem ingredientItem = ingredientService.createIngredientItem(
                requestDto.getRestaurantId(),
                requestDto.getName(),
                requestDto.getCategoryId()
        );

        return new ResponseEntity<>(ingredientItem, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientItem> updateIngredientStoke(@PathVariable Long id) {
        IngredientItem ingredientItem = ingredientService.updateStock(id);

        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientItem>> getAllByRestaurantId(@PathVariable Long restaurantId) {
        List<IngredientItem> restaurantsIngredients = ingredientService.findRestaurantsIngredients(restaurantId);

        return new ResponseEntity<>(restaurantsIngredients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurant/{restaurantId}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long restaurantId) {
        List<IngredientCategory> ingredientCategories = ingredientService.findByRestaurantId(restaurantId);

        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }
}
