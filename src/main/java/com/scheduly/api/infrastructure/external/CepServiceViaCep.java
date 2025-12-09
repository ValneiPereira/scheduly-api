package com.scheduly.api.infrastructure.external;

import com.scheduly.api.config.ApiProperties;
import com.scheduly.api.infrastructure.external.dto.ViaCepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Serviço de integração com ViaCEP usando RestClient
 */
@Service
@Slf4j
public class CepServiceViaCep {

    public static final String CEP_JSON = "/{cep}/json/";
    private final RestClient restClient;

    public CepServiceViaCep(ApiProperties apiProperties) {
        this.restClient = RestClient.builder()
                .baseUrl(apiProperties.getHost())
                .build();
    }

    public ViaCepResponse findAddressByCep(String cep) {
        log.info("Buscando endereço para CEP: {}", cep);
        var cleanCep = cep.replaceAll("[^0-9]", "");

        if (cleanCep.length() != 8) {
            throw new IllegalArgumentException("CEP inválido. Deve conter 8 dígitos.");
        }

        try {

            var response = restClient.get()
                    .uri(CEP_JSON, cleanCep)
                    .retrieve()
                    .body(ViaCepResponse.class);

            if (response == null || Boolean.TRUE.equals(response.getErro())) {
                throw new RuntimeException("CEP não encontrado: " + cep);
            }

            log.info("Endereço encontrado para CEP {}: ", cep);
            return response;

        } catch (Exception e) {
            log.error("Erro ao buscar CEP {}: {}", cep, e.getMessage());
            throw new RuntimeException("Erro ao consultar CEP: " + e.getMessage(), e);
        }
    }
}
