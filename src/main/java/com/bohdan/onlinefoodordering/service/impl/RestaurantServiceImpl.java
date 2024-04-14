package com.bohdan.onlinefoodordering.service.impl;

import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantDto;
import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantRequestDto;
import com.bohdan.onlinefoodordering.mapper.RestaurantMapper;
import com.bohdan.onlinefoodordering.model.Address;
import com.bohdan.onlinefoodordering.model.Restaurant;
import com.bohdan.onlinefoodordering.model.User;
import com.bohdan.onlinefoodordering.repository.AddressRepository;
import com.bohdan.onlinefoodordering.repository.RestaurantRepository;
import com.bohdan.onlinefoodordering.repository.UserRepository;
import com.bohdan.onlinefoodordering.service.RestaurantService;
import com.bohdan.onlinefoodordering.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RestaurantMapper restaurantMapper;

    @Override
    public Restaurant create(RestaurantRequestDto requestDto, Long userId) {
        Address address = addressRepository.save(requestDto.getAddress());
        User user = userService.findUserById(userId);

        Restaurant restaurant = restaurantMapper.toEntity(requestDto);
        restaurant.setAddress(address);
        restaurant.setOwner(user);
        restaurant.setRegistrationDate(LocalDateTime.now());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Long restaurantId, RestaurantRequestDto requestDto) {
        Restaurant restaurant = findById(restaurantId);

        restaurantMapper.updateRestaurantFromDto(requestDto, restaurant);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(Long restaurantId) {
        Restaurant restaurant = findById(restaurantId);

        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> search(String name, String cuisineType) {
        return restaurantRepository.searchByNameAndCuisineType(name, cuisineType);
    }

    @Override
    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id: " + restaurantId + " not found"));
    }

    @Override
    public Restaurant getByUserId(Long userId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if (restaurant == null) {
            throw new EntityNotFoundException("Cannot find restaurant with owner id: " + userId);
        }

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, Long userId) {
        Restaurant restaurant = findById(restaurantId);
        User user = userService.findUserById(userId);

        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurant.getId());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setDescription(restaurant.getDescription());

        if (user.getFavorites().stream()
                .anyMatch(favorite -> favorite.getId().equals(dto.getId()))) {
            user.getFavorites().removeIf(favorite -> favorite.getId().equals(dto.getId()));

            userRepository.save(user);
            return dto;
        }

        user.getFavorites().add(dto);

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateStatus(Long restaurantId) {
        Restaurant restaurant = findById(restaurantId);

        restaurant.setOpen(!restaurant.isOpen());
        return  restaurantRepository.save(restaurant);
    }
}
