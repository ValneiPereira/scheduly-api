package com.scheduly.api.web.controllers;

import com.scheduly.api.ClientsApi;
import com.scheduly.api.application.client.*;
import com.scheduly.api.domain.client.Client;
import com.scheduly.api.web.dtos.ClientRequest;
import com.scheduly.api.web.dtos.ClientResponse;
import com.scheduly.api.web.mappers.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de clientes
 */
@RestController
@RequiredArgsConstructor
public class ClientController implements ClientsApi {

    private final CreateClientUseCase createClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final ListClientsUseCase listClientsUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final ClientMapper clientMapper;

    @Override
    public ResponseEntity<ClientResponse> createClient(ClientRequest request) {
        var client = clientMapper.toDomain(request);
        var created = createClientUseCase.execute(client);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(clientMapper.toResponse(created));
    }

    @Override
    public ResponseEntity<ClientResponse> getClient(Long id) {
        Client client = getClientUseCase.execute(id);
        ClientResponse response = clientMapper.toResponse(client);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ClientResponse>> getClientByName(@RequestParam String name) {
        List<Client> clients = getClientUseCase.execute(name);
        List<ClientResponse> response = clients.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<ClientResponse>> listClients() {
        List<Client> clients = listClientsUseCase.execute();
        List<ClientResponse> responses = clients.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<ClientResponse> updateClient(Long id, ClientRequest request) {
        Client client = clientMapper.toDomain(request);
        Client updatedClient = updateClientUseCase.execute(id, client);
        ClientResponse response = clientMapper.toResponse(updatedClient);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteClient(Long id) {
        deleteClientUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
