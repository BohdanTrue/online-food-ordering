package com.bohdan.onlinefoodordering.mapper;

import com.bohdan.onlinefoodordering.config.MapperConfig;
import com.bohdan.onlinefoodordering.dto.restaurant.RestaurantRequestDto;
import com.bohdan.onlinefoodordering.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface RestaurantMapper {
    Restaurant toEntity(RestaurantRequestDto requestDto);

    void updateRestaurantFromDto(RestaurantRequestDto requestDto, @MappingTarget Restaurant restaurant);
}
