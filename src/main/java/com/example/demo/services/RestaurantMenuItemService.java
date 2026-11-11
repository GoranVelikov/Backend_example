package com.example.demo.services;

import com.example.demo.dto.MenuItemResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.exception.RestaurantNotFoundException;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantMenuItemService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public RestaurantMenuItemService(
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public PageResponse<MenuItemResponse> getMenuItemsByRestaurant(
            Long restaurantId,
            Pageable pageable) {

        restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(restaurantId));

        Page<MenuItemResponse> page = menuItemRepository
                .findByRestaurantId(restaurantId, pageable)
                .map(menuItem -> new MenuItemResponse(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getPrice(),
                        menuItem.getRestaurant().getId(),
                        menuItem.getVersion(),
                        menuItem.getCategory()
                ));

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}