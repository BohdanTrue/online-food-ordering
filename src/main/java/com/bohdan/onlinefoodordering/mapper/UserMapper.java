package com.bohdan.onlinefoodordering.mapper;

import com.bohdan.onlinefoodordering.config.MapperConfig;
import com.bohdan.onlinefoodordering.dto.user.UserResponseDto;
import com.bohdan.onlinefoodordering.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
//    User toEntity(UserRequestDto requestDto);

    UserResponseDto toDto(User user);
}
