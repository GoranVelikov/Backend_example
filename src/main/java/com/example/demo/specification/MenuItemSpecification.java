package com.example.demo.specification;

import com.example.demo.models.MenuItem;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class MenuItemSpecification {

    public static Specification<MenuItem> hasRestaurantId(Long restaurantId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("restaurant").get("id"), restaurantId);
    }

    public static Specification<MenuItem> hasMinPrice(BigDecimal minPrice) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }
    public static Specification<MenuItem> hasMaxPrice(BigDecimal maxPrice) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<MenuItem> hasName(String name) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
