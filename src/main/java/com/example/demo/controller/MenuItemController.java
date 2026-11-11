package com.example.demo.controller;

import com.example.demo.dto.MenuItemPatchRequest;
import com.example.demo.dto.MenuItemRequest;
import com.example.demo.dto.MenuItemResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.exception.ETagMismatchException;
import com.example.demo.services.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/menu-items")
public class MenuItemController {


    //Test
    @GetMapping("/debug-user")
    public String debugUser(Authentication authentication) {
        return authentication.getName() + " " +
                authentication.getAuthorities();
    }

    private final MenuItemService menuItemService;


    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public Page<MenuItemResponse> getMenuItems(Pageable pageable) {
        return menuItemService.getAllMenuItems(pageable);
    }

    @Operation(
            summary = "Get a menu item",
            description = "Returns a menu item by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Menu item found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid menu item ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Menu item not found",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(
                                    implementation = ProblemDetail.class
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id) {

        MenuItemResponse response = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok().eTag("\"" + response.getVersion() + "\"").body(response);
    }

    @GetMapping("/search")
    public PageResponse<MenuItemResponse> searchMenuItems(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String name,
            Pageable pageable) {

        return menuItemService.searchMenuItems(
                restaurantId,
                minPrice,
                maxPrice,
                name,
                pageable
        );
    }

    @GetMapping("/expensive")
    public List<MenuItemResponse> getMoreExpensiveThan(
            @RequestParam BigDecimal price) {

        return menuItemService.getMoreExpensiveThan(price);
    }

    @Operation(
            summary = "Partially update a menu item",
            description = "Updates only the fields provided in the request. " +
                    "The If-Match header must contain the current ETag."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Menu item updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Menu item or restaurant not found"
            ),
            @ApiResponse(
                    responseCode = "412",
                    description = "The If-Match ETag does not match the current resource version"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The resource was modified concurrently"
            )
    })
    @PatchMapping("/{id}")
    public MenuItemResponse patchMenuItem(
            @PathVariable Long id,

            @Parameter(
                    description = "ETag of the version being updated, for example \"3\"",
                    example = "\"3\"",
                    required = true
            )
            @RequestHeader(value = "If-Match",required = false) String ifMatch,

            @Valid @RequestBody MenuItemPatchRequest request) {

        Long version = parseETag(ifMatch);

        return menuItemService.patchMenuItem(id, request, version);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemResponse createMenuItem(@Valid @RequestBody MenuItemRequest request) {
        return menuItemService.createMenuItem(request);
    }

    @GetMapping("/with-restaurants")
    public List<MenuItemResponse> getAllMenuItemsWithRestaurant() {
        return menuItemService.getAllMenuItemsWithRestaurant();
    }

    @PutMapping("/{id}")
    public MenuItemResponse updateMenuItem( @PathVariable Long id,@RequestBody MenuItemRequest request) {
        return menuItemService.updateMenuItem(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }

    //Helper
    private Long parseETag(String ifMatch) {

        if (ifMatch == null || !ifMatch.startsWith("\"")
                || !ifMatch.endsWith("\"")) {
            throw new ETagMismatchException();
        }

        String version = ifMatch.substring(1, ifMatch.length() - 1);

        try {
            return Long.parseLong(version);
        } catch (NumberFormatException exception) {
            throw new ETagMismatchException();
        }
    }
}