package com.bohdan.onlinefoodordering.repository;

import com.bohdan.onlinefoodordering.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByOwnerId(Long userId);

    @Query("SELECT r FROM Restaurant r " +
            "WHERE lower(r.name) LIKE lower(concat('%', :name, '%'))" +
            "OR lower(r.cuisineType) LIKE lower(concat('%', :cuisineType, '%'))")
    List<Restaurant> searchByNameAndCuisineType(@Param("name") String name, @Param("cuisineType") String cuisineType);
}
