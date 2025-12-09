package com.scheduly.api.domain.client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(Long id);
    List<Client> findAll();
    void deleteById(Long id);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByCpfAndIdNot(String cpf, Long id);
}
