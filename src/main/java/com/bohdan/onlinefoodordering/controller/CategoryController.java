package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.model.Category;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.repository.CategoryRepository;
import com.bohdan.onlinefoodordering.service.CategoryService;
import com.bohdan.onlinefoodordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Category> create(
            @RequestBody Category category,
            Authentication authentication
    ) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
//
        Category createdCategory = categoryService.create(category.getName(), user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<Category>> getRestaurantCategories(@PathVariable Long restaurantId) {
        List<Category> categories = categoryService.findByRestaurantId(restaurantId);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
