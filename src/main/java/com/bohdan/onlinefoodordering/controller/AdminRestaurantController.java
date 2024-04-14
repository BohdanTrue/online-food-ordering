package com.bohdan.onlinefoodordering.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/restaurants")
@RequiredArgsConstructor
public class AdminRestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Restaurant> create(
            @RequestBody RestaurantRequestDto requestDto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Restaurant restaurant = restaurantService.create(requestDto, user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> update(
            @RequestBody RestaurantRequestDto requestDto,
            @PathVariable Long id
    ) {
        Restaurant restaurant = restaurantService.update(id, requestDto);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurant> delete(@PathVariable Long id) {
        restaurantService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateStatus(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.updateStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<Restaurant> getByUserId(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Restaurant restaurant = restaurantService.getByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
