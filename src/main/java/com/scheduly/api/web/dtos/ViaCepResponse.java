package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO de resposta da API ViaCEP
 */
public record ViaCepResponse(
        @JsonProperty("cep") String cep,
        @JsonProperty("logradouro") String logradouro,
        @JsonProperty("complemento") String complemento,
        @JsonProperty("bairro") String bairro,
        @JsonProperty("localidade") String localidade,
        @JsonProperty("uf") String uf,
        @JsonProperty("erro") Boolean erro) {
}
