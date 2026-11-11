package com.example.demo.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category of a menu item")
public enum MenuItemCategory {
    APPETIZER,
    MAIN_COURSE,
    DESSERT,
    DRINK
}
