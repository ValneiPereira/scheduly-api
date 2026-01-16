package com.scheduly.api.infrastructure.persistence.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.infrastructure.persistence.address.AddressEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final ClientEntityMapper clientMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Client save(Client client) {
        ClientEntity entity;

        if (client.getId() != null) {
            // Atualização: carregar entidade gerenciada
            ClientEntity existingEntity = jpaRepository.findById(client.getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + client.getId()));

            // Atualizar campos do cliente apenas se forem fornecidos (não-null)
            if (client.getName() != null) {
                existingEntity.setName(client.getName());
            }
            if (client.getEmail() != null) {
                existingEntity.setEmail(client.getEmail());
            }
            if (client.getPhone() != null) {
                existingEntity.setPhone(client.getPhone());
            }
            if (client.getAvatarUrl() != null) {
                System.out.println("[ClientRepositoryImpl] Setando avatarUrl: " + client.getAvatarUrl());
                existingEntity.setAvatarUrl(client.getAvatarUrl());
            }

            // Atualizar endereço apenas se for fornecido
            if (client.getAddress() != null) {
                if (existingEntity.getAddress() != null) {
                    // Atualizar endereço existente (apenas campos não-null)
                    AddressEntity existingAddress = existingEntity.getAddress();
                    
                    if (client.getAddress().getStreet() != null) {
                        existingAddress.setStreet(client.getAddress().getStreet());
                    }
                    if (client.getAddress().getNumber() != null) {
                        existingAddress.setNumber(client.getAddress().getNumber());
                    }
                    if (client.getAddress().getComplement() != null) {
                        existingAddress.setComplement(client.getAddress().getComplement());
                    }
                    if (client.getAddress().getNeighborhood() != null) {
                        existingAddress.setNeighborhood(client.getAddress().getNeighborhood());
                    }
                    if (client.getAddress().getCity() != null) {
                        existingAddress.setCity(client.getAddress().getCity());
                    }
                    if (client.getAddress().getState() != null) {
                        existingAddress.setState(client.getAddress().getState());
                    }
                    if (client.getAddress().getZipCode() != null) {
                        existingAddress.setZipCode(client.getAddress().getZipCode());
                    }
                    existingAddress.setOwnerId(existingEntity.getId());
                    existingAddress.setOwnerType("CLIENT");
                } else {
                    // Criar novo endereço se não existir
                    AddressEntity newAddress = clientMapper.getAddressMapper().toEntity(client.getAddress());
                    newAddress.setOwnerId(existingEntity.getId());
                    newAddress.setOwnerType("CLIENT");
                    existingEntity.setAddress(newAddress);
                }
            }

            entity = existingEntity;
        } else {
            // Criação: criar nova entidade
            entity = clientMapper.toEntity(client);
        }

        // Salvar cliente (endereço será salvo via cascade)
        ClientEntity saved = jpaRepository.save(entity);

        // Forçar flush para garantir que o ID do cliente seja gerado
        entityManager.flush();

        // Atualizar ownerId e ownerType do endereço após o cliente ter ID (apenas na criação)
        if (client.getId() == null && saved.getAddress() != null) {
            saved.getAddress().setOwnerId(saved.getId());
            saved.getAddress().setOwnerType("CLIENT");
            // O endereço já está gerenciado, então será persistido automaticamente
        }

        return clientMapper.toDomain(saved);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return jpaRepository.findById(id)
                .map(clientMapper::toDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(clientMapper::toDomain);
    }

    @Override
    public List<Client> findByName(String name) {
        return jpaRepository.findByName(name).stream()
                .map(clientMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream()
                .map(clientMapper::toDomain)
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
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return jpaRepository.findByEmail(email)
                .map(ClientEntity::getId)
                .filter(foundId -> !foundId.equals(id))
                .isPresent();
    }
}
