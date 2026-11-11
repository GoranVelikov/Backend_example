package com.example.demo.services;

import com.example.demo.exception.RestaurantNotFoundException;
import com.example.demo.models.Restaurant;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestService {
    private final RestaurantRepository repository;

    public RestService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }


    @Cacheable(value = "restaurants", key = "#id")
    @Transactional(readOnly = true)
    public Restaurant getRestaurantById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants",key = "#id")
    @Transactional
    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {
        Restaurant updatedRestaurant = repository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));

        updatedRestaurant.setName(restaurant.getName());

        return updatedRestaurant;
    }

    @CacheEvict(value = "restaurants",key = "#id")
    public void deleteRestaurant(Long id) {
        Restaurant restaurant= repository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        repository.delete(restaurant);
    }
}