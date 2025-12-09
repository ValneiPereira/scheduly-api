package com.scheduly.api.web.controllers;

import com.scheduly.api.application.usecases.client.*;
import com.scheduly.api.domain.client.Client;
import com.scheduly.api.web.dtos.ClientRequest;
import com.scheduly.api.web.dtos.ClientResponse;
import com.scheduly.api.web.mappers.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de clientes
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final ClientMapper clientMapper;

    /**
     * Criar novo cliente
     */
    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        Client client = clientMapper.toDomain(request);
        Client createdClient = createClientUseCase.execute(client);
        ClientResponse response = clientMapper.toResponse(createdClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        Client client = getClientUseCase.execute(id);
        ClientResponse response = clientMapper.toResponse(client);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<Client> clients = listClientsUseCase.execute();
        List<ClientResponse> responses = clients.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request) {
        Client client = clientMapper.toDomain(request);
        Client updatedClient = updateClientUseCase.execute(id, client);
        ClientResponse response = clientMapper.toResponse(updatedClient);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        deleteClientUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
