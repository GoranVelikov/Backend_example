package com.example.demo.exception;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(Long id) {
        super("Could not find Menu Item with id " + id);
    }
}
