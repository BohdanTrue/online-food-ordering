package com.bohdan.onlinefoodordering.service.impl;

import com.bohdan.onlinefoodordering.model.IngredientCategory;
import com.bohdan.onlinefoodordering.model.IngredientItem;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.repository.IngredientCategoryRepository;
import com.bohdan.onlinefoodordering.repository.IngredientItemRepository;
import com.bohdan.onlinefoodordering.service.IngredientService;
import com.bohdan.onlinefoodordering.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientItemRepository ingredientItemRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) {
        return ingredientCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find ingredient category by id: " + id));
    }

    @Override
    public List<IngredientCategory> findByRestaurantId(Long restaurantId) {
        restaurantService.findById(restaurantId);

        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<IngredientItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItem createIngredientItem(Long restaurantId, String name, Long categoryId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientItem ingredientItem = new IngredientItem();
        ingredientItem.setName(name);
        ingredientItem.setRestaurant(restaurant);
        ingredientItem.setCategory(category);

        IngredientItem ingredient = ingredientItemRepository.save(ingredientItem);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public IngredientItem updateStock(Long id) {
        IngredientItem ingredientItem = ingredientItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find ingredient item by id: " + id));

        ingredientItem.setInStock(!ingredientItem.isInStock());

        return ingredientItemRepository.save(ingredientItem);
    }
}
