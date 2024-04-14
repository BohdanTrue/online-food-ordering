package com.bohdan.onlinefoodordering.dto.food;

import com.bohdan.onlinefoodordering.model.Category;
import com.bohdan.onlinefoodordering.model.IngredientItem;
import lombok.Data;

import java.util.List;

@Data
public class FoodRequestDto {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientItem> ingredientsItems;
}
