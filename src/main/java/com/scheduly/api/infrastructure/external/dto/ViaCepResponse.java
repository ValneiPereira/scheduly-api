package com.scheduly.api.infrastructure.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO de resposta da API ViaCEP
 */
@Data
public class ViaCepResponse {

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("complemento")
    private String complemento;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("localidade")
    private String localidade;

    @JsonProperty("uf")
    private String uf;

    @JsonProperty("erro")
    private Boolean erro;
}
