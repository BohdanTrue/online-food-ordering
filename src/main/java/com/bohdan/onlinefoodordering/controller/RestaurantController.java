package com.bohdan.onlinefoodordering.controller;

import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantDto;
import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantRequestDto;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.service.RestaurantService;
import com.bohdan.onlinefoodordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER', 'ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> search(@RequestParam(required = false) String name, @RequestParam(required = false) String cuisineType) {
        List<Restaurant> restaurants = restaurantService.search(name, cuisineType);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER', 'ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {
        List<Restaurant> restaurants = restaurantService.getAll();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER', 'ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT_OWNER', 'ROLE_RESTAURANT_OWNER', 'ROLE_ADMIN')")
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            Authentication authentication,
            @PathVariable Long id
    ) {
        User user = (User) authentication.getPrincipal();

        RestaurantDto restaurantDto = restaurantService.addToFavorites(id, user.getId());

        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }
}
