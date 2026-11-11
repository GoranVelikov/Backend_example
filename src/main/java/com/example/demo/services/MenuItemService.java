package com.example.demo.services;

import com.example.demo.dto.MenuItemPatchRequest;
import com.example.demo.dto.MenuItemRequest;
import com.example.demo.dto.MenuItemResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.exception.ETagMismatchException;
import com.example.demo.exception.MenuItemNotFoundException;
import com.example.demo.exception.RestaurantNotFoundException;
import com.example.demo.mapper.MenuItemMapper;
import com.example.demo.models.MenuItem;
import com.example.demo.models.Restaurant;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.specification.MenuItemSpecification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuItemService(
            MenuItemRepository repository,
            RestaurantRepository restaurantRepository,
            MenuItemMapper menuItemMapper) {

        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemMapper = menuItemMapper;
    }

    public Page<MenuItemResponse> getAllMenuItems(Pageable pageable){
        return repository.findAll(pageable).map(menuItemMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public MenuItemResponse getMenuItemById(Long id){

        MenuItem menuItem = repository.findById(id).orElseThrow(() -> new MenuItemNotFoundException(id));

        return menuItemMapper.toResponse(menuItem);
    }

    public PageResponse<MenuItemResponse> getMenuItemsByRestaurant(Long restaurantId, Pageable pageable) {

        Page<MenuItemResponse> page = repository.findByRestaurantId(restaurantId, pageable).map(menuItemMapper::toResponse);

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private MenuItem getMenuItemEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new MenuItemNotFoundException(id));
    }

    @Transactional
    public MenuItemResponse createMenuItem(MenuItemRequest request){

        Restaurant restaurant= restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(request.getRestaurantId()));

        MenuItem menuItem = menuItemMapper.toEntity(request);
        menuItem.setRestaurant(restaurant);

        MenuItem savedMenuItem = repository.save(menuItem);

        return menuItemMapper.toResponse(savedMenuItem);
    }

    @Transactional
    public MenuItemResponse updateMenuItem(Long id,MenuItemRequest request){

        MenuItem oldmenuItem = getMenuItemEntityById(id);
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(request.getRestaurantId()));
        oldmenuItem.setName(request.getName());
        oldmenuItem.setPrice(request.getPrice());
        oldmenuItem.setRestaurant(restaurant);
        oldmenuItem.setCategory(request.getCategory());

        return menuItemMapper.toResponse(oldmenuItem);
    }

    public List<MenuItemResponse> getMoreExpensiveThan(BigDecimal price) {

        return repository.findMoreExpensiveThan(price)
                .stream()
                .map(menuItemMapper::toResponse)
                .toList();
    }

    public void deleteMenuItem(Long id){
        MenuItem oldMenuItem = getMenuItemEntityById(id);
        repository.delete(oldMenuItem);

    }

    public List<MenuItemResponse> getAllMenuItemsWithRestaurant() {

        return repository.findAllWithRestaurant()
                .stream()
                .map(menuItemMapper::toResponse)
                .toList();
    }

    public PageResponse<MenuItemResponse> searchMenuItems(Long restaurantId, BigDecimal minPrice, BigDecimal maxPrice, String name, Pageable pageable) {

        Specification<MenuItem> specification = Specification.where(null);

        if (restaurantId != null) {
            specification=specification.and(MenuItemSpecification.hasRestaurantId(restaurantId));
        }

        if (minPrice != null) {
            specification=specification.and(MenuItemSpecification.hasMinPrice(minPrice));
        }

        if (maxPrice != null) {
            specification=specification.and(MenuItemSpecification.hasMaxPrice(maxPrice));
        }

        if (name != null && !name.isBlank()) {
            specification = specification.and(MenuItemSpecification.hasName(name));
        }

        Page<MenuItemResponse> page = repository
                .findAll(specification, pageable).map(menuItemMapper::toResponse);

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Transactional
    public MenuItemResponse patchMenuItem(
            Long id,
            MenuItemPatchRequest request,
            Long version) {

        MenuItem menuItem = getMenuItemEntityById(id);

        if (!menuItem.getVersion().equals(version)) {
            throw new ETagMismatchException();
        }

        menuItemMapper.updateEntity(request, menuItem);

        if (request.getRestaurantId() != null) {

            Restaurant restaurant = restaurantRepository
                    .findById(request.getRestaurantId())
                    .orElseThrow(() ->
                            new RestaurantNotFoundException(
                                    request.getRestaurantId()
                            ));

            menuItem.setRestaurant(restaurant);
        }
        if(request.getCategory() != null) {
            menuItem.setCategory(request.getCategory());
        }

        return menuItemMapper.toResponse(menuItem);
    }
}
