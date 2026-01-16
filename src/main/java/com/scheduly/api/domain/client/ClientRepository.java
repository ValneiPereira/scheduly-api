package com.scheduly.api.domain.client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);

    Optional<Client> findById(Long id);

    Optional<Client> findByEmail(String email);

    List<Client> findByName(String name);

    List<Client> findAll();

    void deleteById(Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
