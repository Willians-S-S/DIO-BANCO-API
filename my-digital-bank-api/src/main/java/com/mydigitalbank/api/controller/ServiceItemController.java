package com.mydigitalbank.api.controller;

import com.mydigitalbank.api.domain.model.ServiceItem;
import com.mydigitalbank.api.service.ServiceItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/service-items")
@Tag(name = "Service Items", description = "Endpoints for Managing Available Service Items")
public class ServiceItemController {

    private final ServiceItemService serviceItemService;

    public ServiceItemController(ServiceItemService serviceItemService) {
        this.serviceItemService = serviceItemService;
    }

    @GetMapping
    @Operation(summary = "Get all service items")
    public ResponseEntity<List<ServiceItem>> getAllServiceItems() {
        return ResponseEntity.ok(serviceItemService.findAll());
    }

    @GetMapping("/<built-in function id>")
    @Operation(summary = "Get a service item by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the service item"),
        @ApiResponse(responseCode = "404", description = "Service item not found", content = @Content)
    })
    public ResponseEntity<ServiceItem> getServiceItemById(@Parameter(description = "ID of service item") @PathVariable Long id) {
        return ResponseEntity.ok(serviceItemService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new service item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Service item created"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<ServiceItem> createServiceItem(@Valid @RequestBody ServiceItem itemToCreate) {
        ServiceItem itemCreated = serviceItemService.create(itemToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/<built-in function id>")
                .buildAndExpand(itemCreated.getItemId())
                .toUri();
        return ResponseEntity.created(location).body(itemCreated);
    }

    @PutMapping("/<built-in function id>")
    @Operation(summary = "Update an existing service item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Service item updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Service item not found", content = @Content)
    })
    public ResponseEntity<ServiceItem> updateServiceItem(@Parameter(description = "ID of service item to update") @PathVariable Long id,
                                                      @Valid @RequestBody ServiceItem itemToUpdate) {
        return ResponseEntity.ok(serviceItemService.update(id, itemToUpdate));
    }

    @DeleteMapping("/<built-in function id>")
    @Operation(summary = "Delete a service item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Service item deleted"),
        @ApiResponse(responseCode = "404", description = "Service item not found", content = @Content)
    })
    public ResponseEntity<Void> deleteServiceItem(@Parameter(description = "ID of service item to delete") @PathVariable Long id) {
        serviceItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
