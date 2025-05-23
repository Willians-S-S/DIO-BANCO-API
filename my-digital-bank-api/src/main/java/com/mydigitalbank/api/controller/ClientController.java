package com.mydigitalbank.api.controller;

import com.mydigitalbank.api.domain.model.Client;
import com.mydigitalbank.api.service.ClientService;
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
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Endpoints for Managing Bank Clients and their Financial Profiles")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieve a list of all registered clients.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)))
    })
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/<built-in function id>")
    @Operation(summary = "Get a client by ID", description = "Retrieve a specific client by their unique ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved client",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "404", description = "Client not found", content = @Content)
    })
    public ResponseEntity<Client> getClientById(@Parameter(description = "ID of client to be retrieved") @PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

   @GetMapping("/document/{document}")
    @Operation(summary = "Get a client by Document", description = "Retrieve a specific client by their document (CPF/CNPJ).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved client",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "404", description = "Client not found for the given document", content = @Content)
    })
    public ResponseEntity<Client> getClientByDocument(@Parameter(description = "Document (CPF/CNPJ) of the client") @PathVariable String document) {
        return ResponseEntity.ok(clientService.findByDocument(document));
    }

    @PostMapping
    @Operation(summary = "Create a new client", description = "Register a new client along with their financial profile if provided.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Client created successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data provided (validation error)", content = @Content),
        @ApiResponse(responseCode = "422", description = "Business rule violation (e.g., client document or account number already exists)", content = @Content)
    })
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client clientToCreate) {
        Client clientCreated = clientService.create(clientToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/<built-in function id>")
                .buildAndExpand(clientCreated.getClientId())
                .toUri();
        return ResponseEntity.created(location).body(clientCreated);
    }

    @PutMapping("/<built-in function id>")
    @Operation(summary = "Update an existing client", description = "Update the details of an existing client and/or their financial profile.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client updated successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content),
        @ApiResponse(responseCode = "404", description = "Client not found", content = @Content),
        @ApiResponse(responseCode = "422", description = "Business rule violation", content = @Content)
    })
    public ResponseEntity<Client> updateClient(@Parameter(description = "ID of client to be updated") @PathVariable Long id,
                                             @Valid @RequestBody Client clientToUpdate) {
        Client clientUpdated = clientService.update(id, clientToUpdate);
        return ResponseEntity.ok(clientUpdated);
    }

    @DeleteMapping("/<built-in function id>")
    @Operation(summary = "Delete a client", description = "Remove a client and their associated financial profile by ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Client deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Client not found", content = @Content)
    })
    public ResponseEntity<Void> deleteClient(@Parameter(description = "ID of client to be deleted") @PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
