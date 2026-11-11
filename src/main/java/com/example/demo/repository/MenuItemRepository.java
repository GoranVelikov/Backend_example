package com.example.demo.repository;

import com.example.demo.models.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {


    @Query("SELECT  m FROM  MenuItem m WHERE m.price> :price")
    List<MenuItem> findMoreExpensiveThan(BigDecimal price);

    @Query("""
    SELECT m
    FROM MenuItem m
    JOIN FETCH m.restaurant
""")
    List<MenuItem> findAllWithRestaurant();

    List<MenuItem> findByRestaurantId(Long restaurantId);

        Page<MenuItem> findByRestaurantId(
                Long restaurantId,
                Pageable pageable
        );

}
