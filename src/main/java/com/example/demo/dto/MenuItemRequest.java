package com.example.demo.dto;

import com.example.demo.models.MenuItemCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class MenuItemRequest {

    @NotBlank
    @Schema(
            description = "Name of the menu item",
            example = "Margherita Pizza"
    )
    private String name;

    @NotNull
    @Positive
    @Schema(
            description = "Price of the menu item",
            example = "8.50"
    )
    private BigDecimal  price;

    @NotNull
    @Schema(
            description = "ID of the restaurant that owns this menu item",
            example = "1"
    )
    private Long restaurantId;

    @Schema(
            description = "Category of the menu item",
            example = "MAIN_COURSE"
    )
    private MenuItemCategory category;

    public MenuItemRequest() {
    }

    public MenuItemRequest(String name, BigDecimal price, Long restaurantId) {
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public MenuItemCategory getCategory() {
        return category;
    }

    public void setCategory(MenuItemCategory category) {
        this.category = category;
    }
}
