package com.bohdan.onlinefoodordering.service;

import com.bohdan.onlinefoodordering.model.IngredientCategory;
import com.bohdan.onlinefoodordering.model.IngredientItem;

import java.util.List;

public interface IngredientService {
    IngredientCategory createIngredientCategory(String name, Long restaurantId);

    IngredientCategory findIngredientCategoryById(Long id);

    List<IngredientCategory> findByRestaurantId(Long restaurantId);

    List<IngredientItem> findRestaurantsIngredients(Long restaurantId);

    IngredientItem createIngredientItem(
            Long restaurantId,
            String name,
            Long categoryId
    );

    IngredientItem updateStock(Long id);
}
