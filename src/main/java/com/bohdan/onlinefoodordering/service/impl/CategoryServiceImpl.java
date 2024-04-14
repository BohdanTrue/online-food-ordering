package com.bohdan.onlinefoodordering.service.impl;

import com.bohdan.onlinefoodordering.model.Category;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.repository.CategoryRepository;
import com.bohdan.onlinefoodordering.repository.RestaurantRepository;
import com.bohdan.onlinefoodordering.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Category create(String name, Long userId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findByRestaurantId(Long restaurantId) {
        return categoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category by id: " + id));
    }
}
