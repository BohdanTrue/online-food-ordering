package com.bohdan.onlinefoodordering.dto.ingredient;

import com.bohdan.onlinefoodordering.model.Restaurant;
import lombok.Data;

@Data
public class IngredientCategoryRequestDto {
    private String name;
    private Long restaurantId;
}
