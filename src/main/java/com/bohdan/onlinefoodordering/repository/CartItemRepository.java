package com.bohdan.onlinefoodordering.repository;

import com.bohdan.onlinefoodordering.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
