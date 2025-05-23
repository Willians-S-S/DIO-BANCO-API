package com.mydigitalbank.api.controller;

import com.mydigitalbank.api.domain.model.UpdateInfo;
import com.mydigitalbank.api.service.UpdateInfoService;
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
@RequestMapping("/update-infos")
@Tag(name = "Update Information", description = "Endpoints for Managing Update Information/News")
public class UpdateInfoController {

    private final UpdateInfoService updateInfoService;

    public UpdateInfoController(UpdateInfoService updateInfoService) {
        this.updateInfoService = updateInfoService;
    }

    @GetMapping
    @Operation(summary = "Get all update information items")
    public ResponseEntity<List<UpdateInfo>> getAllUpdateInfos() {
        return ResponseEntity.ok(updateInfoService.findAll());
    }

    @GetMapping("/<built-in function id>")
    @Operation(summary = "Get an update information item by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the update info"),
        @ApiResponse(responseCode = "404", description = "Update info not found", content = @Content)
    })
    public ResponseEntity<UpdateInfo> getUpdateInfoById(@Parameter(description = "ID of update info") @PathVariable Long id) {
        return ResponseEntity.ok(updateInfoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new update information item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Update info created"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<UpdateInfo> createUpdateInfo(@Valid @RequestBody UpdateInfo infoToCreate) {
        UpdateInfo infoCreated = updateInfoService.create(infoToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/<built-in function id>")
                .buildAndExpand(infoCreated.getInfoId())
                .toUri();
        return ResponseEntity.created(location).body(infoCreated);
    }

    @PutMapping("/<built-in function id>")
    @Operation(summary = "Update an existing update information item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update info updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Update info not found", content = @Content)
    })
    public ResponseEntity<UpdateInfo> updateUpdateInfo(@Parameter(description = "ID of update info to update") @PathVariable Long id,
                                                    @Valid @RequestBody UpdateInfo infoToUpdate) {
        return ResponseEntity.ok(updateInfoService.update(id, infoToUpdate));
    }

    @DeleteMapping("/<built-in function id>")
    @Operation(summary = "Delete an update information item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Update info deleted"),
        @ApiResponse(responseCode = "404", description = "Update info not found", content = @Content)
    })
    public ResponseEntity<Void> deleteUpdateInfo(@Parameter(description = "ID of update info to delete") @PathVariable Long id) {
        updateInfoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
