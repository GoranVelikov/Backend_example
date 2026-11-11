package com.example.demo.dto;

import com.example.demo.models.MenuItemCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class MenuItemResponse {

    @Schema(
            description = "Unique ID of the menu item",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "Name of the menu item",
            example = "Margherita Pizza"
    )
    private String name;
    @Schema(
            description = "Price of the menu item",
            example = "8.50"
    )
    private BigDecimal price;
    @Schema(
            description = "ID of the restaurant",
            example = "1"
    )
    private Long restaurantId;
    @Schema(
            description = "Current version used for optimistic locking",
            example = "3"
    )
    private Long version;
    @Schema(
            description = "Category of the menu item",
            example = "MAIN_COURSE"
    )
    private MenuItemCategory category;

    public MenuItemResponse() {}

    public MenuItemResponse(Long id, String name, BigDecimal price, Long restaurantId,Long version, MenuItemCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
        this.version = version;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public MenuItemCategory getCategory() {
        return category;
    }

    public void setCategory(MenuItemCategory category) {
        this.category = category;
    }
}
