package com.bohdan.onlinefoodordering.repository;

import com.bohdan.onlinefoodordering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(Long userId);
}
