package com.bohdan.onlinefoodordering.dto.order;

import com.bohdan.onlinefoodordering.model.Address;
import lombok.Data;

@Data
public class OrderRequestDto {
    private Long restaurantId;
    private Address deliveryAddress;
}
