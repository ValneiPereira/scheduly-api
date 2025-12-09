package com.scheduly.api.infrastructure.persistence.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.common.Address;
import com.scheduly.api.infrastructure.persistence.common.AddressEmbeddable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementação do ClientRepository usando JPA
 */
@Component
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaRepository jpaRepository;

    @Override
    public Client save(Client client) {
        ClientEntity entity = toEntity(client);
        ClientEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return jpaRepository.findByEmail(email)
                .map(ClientEntity::getId)
                .filter(foundId -> !foundId.equals(id))
                .isPresent();
    }

    @Override
    public boolean existsByCpfAndIdNot(String cpf, Long id) {
        return jpaRepository.findByCpf(cpf)
                .map(ClientEntity::getId)
                .filter(foundId -> !foundId.equals(id))
                .isPresent();
    }

    // Conversões Entity <-> Domain

    private Client toDomain(ClientEntity entity) {
        return Client.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .cpf(entity.getCpf())
                .phone(entity.getPhone())
                .address(toAddressDomain(entity.getAddress()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private ClientEntity toEntity(Client client) {
        return ClientEntity.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .cpf(client.getCpf())
                .phone(client.getPhone())
                .address(toAddressEmbeddable(client.getAddress()))
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    private Address toAddressDomain(AddressEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }
        return Address.builder()
                .street(embeddable.getStreet())
                .number(embeddable.getNumber())
                .complement(embeddable.getComplement())
                .neighborhood(embeddable.getNeighborhood())
                .city(embeddable.getCity())
                .state(embeddable.getState())
                .zipCode(embeddable.getZipCode())
                .build();
    }

    private AddressEmbeddable toAddressEmbeddable(Address address) {
        if (address == null) {
            return null;
        }
        return AddressEmbeddable.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }
}
