package com.example.demo.controller;

import com.example.demo.dto.MenuItemResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.models.Restaurant;
import com.example.demo.services.RestService;
import com.example.demo.services.RestaurantMenuItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/restaurants")
public class HelloController {
    private final RestService service;
    private final RestaurantMenuItemService restaurantMenuItemService;


    public  HelloController(RestService service,  RestaurantMenuItemService restaurantMenuItemService)
    {
        this.service = service;
        this.restaurantMenuItemService = restaurantMenuItemService;
    }

    @GetMapping
    public List<Restaurant> getRestaurants()
    {
        return service.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id)
    {
        return service.getRestaurantById(id);
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant){
        return service.createRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant){
        return service.updateRestaurant(id,restaurant);
    }

    @GetMapping("/{restaurantId}/menu-items")
    public PageResponse<MenuItemResponse> getMenuItems(
            @PathVariable Long restaurantId,
            Pageable pageable) {

        return restaurantMenuItemService
                .getMenuItemsByRestaurant(restaurantId, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable Long id){
        service.deleteRestaurant(id);
    }
}
