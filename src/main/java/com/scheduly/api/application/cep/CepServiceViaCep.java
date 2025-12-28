package com.scheduly.api.application.cep;

import com.scheduly.api.config.ApiProperties;
import com.scheduly.api.domain.exception.BusinessException;
import com.scheduly.api.web.dtos.ViaCepResponse;
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

    @org.springframework.cache.annotation.Cacheable(value = "ceps", key = "#cep")
    public ViaCepResponse findAddressByCep(String cep) {
        log.info("Buscando endereço para CEP: {}", cep);
        var cleanCep = cep.replaceAll("[^0-9]", "");

        if (cleanCep.length() != 8) {
            throw new BusinessException("CEP inválido. Deve conter 8 dígitos.");
        }

        try {

            var response = restClient.get()
                    .uri(CEP_JSON, cleanCep)
                    .retrieve()
                    .body(ViaCepResponse.class);

            if (response == null || Boolean.TRUE.equals(response.erro())) {
                throw new BusinessException("CEP não encontrado: " + cep);
            }

            log.info("Endereço encontrado para CEP {}: ", cep);
            return response;

        } catch (Exception e) {
            log.error("Erro ao buscar CEP {}: {}", cep, e.getMessage());
            throw new BusinessException("Erro ao consultar CEP: " + e.getMessage(), e);
        }
    }
}
