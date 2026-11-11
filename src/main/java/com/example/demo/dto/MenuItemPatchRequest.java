package com.example.demo.dto;

import com.example.demo.models.MenuItemCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class MenuItemPatchRequest {

    @Schema(
            description = "New name of the menu item",
            example = "Double Margherita")
    private String name;

    @Positive
    @Schema(
            description = "New price of the menu item",
            example = "15.00")
    private BigDecimal price;

    @Schema(
            description = "New restaurant ID",
            example = "2")
    private Long restaurantId;

    @Schema(
            description = "New category of the menu item",
            example = "MAIN_COURSE")
    private Long version;

    private MenuItemCategory  category;

    public MenuItemPatchRequest() {
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