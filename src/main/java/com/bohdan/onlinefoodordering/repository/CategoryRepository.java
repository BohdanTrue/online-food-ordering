package com.bohdan.onlinefoodordering.repository;

import com.bohdan.onlinefoodordering.model.Category;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRestaurantId(Long restaurantId);
}
