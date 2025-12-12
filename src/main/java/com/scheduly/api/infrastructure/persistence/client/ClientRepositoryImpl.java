package com.scheduly.api.infrastructure.persistence.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
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
    private final ClientEntityMapper clientMapper;

    @Override
    public Client save(Client client) {
        ClientEntity entity = clientMapper.toEntity(client);
        ClientEntity saved = jpaRepository.save(entity);
        return clientMapper.toDomain(saved);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return jpaRepository.findById(id)
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
}
