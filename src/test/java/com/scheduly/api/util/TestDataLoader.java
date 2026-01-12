package com.scheduly.api.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Classe utilitária para carregar dados de teste de arquivos JSON
 */
public class TestDataLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Carrega um objeto de cliente válido do arquivo valid-clients.json
     *
     * @param key Chave do objeto no JSON (ex: "mariaSilva", "joaoSantos")
     * @return Map com os dados do cliente
     */
    public static Map<String, Object> loadValidClient(String key) {
        return loadClientData("test-data/clients/valid-clients.json", key);
    }

    /**
     * Carrega dados de atualização do arquivo update-clients.json
     *
     * @param key Chave do objeto no JSON (ex: "pedroOliveiraAtualizado")
     * @return Map com os dados para atualização
     */
    public static Map<String, Object> loadUpdateClient(String key) {
        return loadClientData("test-data/clients/update-clients.json", key);
    }

    /**
     * Carrega dados inválidos do arquivo invalid-clients.json
     *
     * @param key Chave do objeto no JSON (ex: "clienteInvalido")
     * @return Map com os dados inválidos
     */
    public static Map<String, Object> loadInvalidClient(String key) {
        return loadClientData("test-data/clients/invalid-clients.json", key);
    }

    /**
     * Método genérico para carregar dados de um arquivo JSON
     *
     * @param filePath Caminho do arquivo JSON no classpath
     * @param key      Chave do objeto a ser carregado
     * @return Map com os dados do objeto
     */
    private static Map<String, Object> loadClientData(String filePath, String key) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            InputStream inputStream = resource.getInputStream();
            
            TypeReference<Map<String, Map<String, Object>>> typeRef = 
                new TypeReference<Map<String, Map<String, Object>>>() {};
            
            Map<String, Map<String, Object>> allData = objectMapper.readValue(inputStream, typeRef);
            
            if (!allData.containsKey(key)) {
                throw new IllegalArgumentException(
                    String.format("Chave '%s' não encontrada no arquivo %s", key, filePath)
                );
            }
            
            return allData.get(key);
        } catch (IOException e) {
            throw new RuntimeException(
                String.format("Erro ao carregar dados de teste do arquivo %s com chave %s", filePath, key),
                e
            );
        }
    }
}
