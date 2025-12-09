package com.scheduly.api.application.usecases.client;

import com.scheduly.api.domain.client.Client;
import com.scheduly.api.domain.client.ClientRepository;
import com.scheduly.api.domain.exception.ConflictException;
import com.scheduly.api.domain.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Criar novo cliente
 */
@Service
@RequiredArgsConstructor
public class CreateClientUseCase {

    private final ClientRepository clientRepository;

    @Transactional
    public Client execute(Client client) {
        // Validar CPF único
        if (clientRepository.existsByCpf(client.getCpf())) {
            throw new ConflictException("CPF já cadastrado: " + client.getCpf());
        }

        // Validar email único
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ConflictException("Email já cadastrado: " + client.getEmail());
        }

        // Validar formato CPF (básico - apenas dígitos)
        if (!isValidCpf(client.getCpf())) {
            throw new ValidationException("CPF inválido: " + client.getCpf());
        }

        // Salvar cliente
        return clientRepository.save(client);
    }

    private boolean isValidCpf(String cpf) {
        // Remove caracteres não numéricos
        String cleanCpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cleanCpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cleanCpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Validação dos dígitos verificadores
        try {
            int[] digits = cleanCpf.chars().map(c -> c - '0').toArray();

            // Primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10)
                firstDigit = 0;

            if (digits[9] != firstDigit) {
                return false;
            }

            // Segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10)
                secondDigit = 0;

            return digits[10] == secondDigit;

        } catch (Exception e) {
            return false;
        }
    }
}
