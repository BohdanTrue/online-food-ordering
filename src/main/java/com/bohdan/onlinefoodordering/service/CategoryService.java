package com.bohdan.onlinefoodordering.service;

import com.bohdan.onlinefoodordering.model.Category;

import java.util.List;

public interface CategoryService {

    Category create(String name, Long userId);

    List<Category> findByRestaurantId(Long restaurantId);

    Category findById(Long id);
}
