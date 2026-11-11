package com.example.demo.mapper;

import com.example.demo.dto.MenuItemPatchRequest;
import com.example.demo.dto.MenuItemRequest;
import com.example.demo.dto.MenuItemResponse;
import com.example.demo.models.MenuItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    @Mapping(
            source = "restaurant.id",
            target = "restaurantId"
    )
    MenuItemResponse toResponse(MenuItem menuItem);

    MenuItem toEntity(MenuItemRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(
            MenuItemPatchRequest request,
            @MappingTarget MenuItem menuItem
    );
}