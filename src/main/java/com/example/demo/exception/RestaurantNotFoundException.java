package com.example.demo.exception;

public class RestaurantNotFoundException extends RuntimeException {

    private final Long id;

    public RestaurantNotFoundException(Long id) {
        super("Restaurant with id " + id + " was not found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
