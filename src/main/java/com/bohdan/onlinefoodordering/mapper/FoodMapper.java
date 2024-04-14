package com.bohdan.onlinefoodordering.mapper;

import com.bohdan.onlinefoodordering.config.MapperConfig;
import com.bohdan.onlinefoodordering.dto.food.FoodRequestDto;
import com.bohdan.onlinefoodordering.model.Food;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface FoodMapper {
    Food toEntity(FoodRequestDto requestDto);
}
