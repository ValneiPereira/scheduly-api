package com.scheduly.api.application.common;

import com.scheduly.api.domain.common.Address;

/**
 * Helper para atualização de endereços.
 * Evita duplicação de código entre UpdateClientUseCase e UpdateProfessionalUseCase.
 */
public class AddressUpdateHelper {

    /**
     * Atualiza um endereço existente com os dados fornecidos (apenas campos não-null).
     * Se o endereço existente for null e um novo for fornecido, retorna o novo endereço.
     * Se nenhum endereço novo for fornecido, retorna o endereço existente sem modificações.
     *
     * @param newAddress Endereço com os novos dados (pode ter campos null)
     * @param existingAddress Endereço existente a ser atualizado
     * @return Endereço atualizado ou o novo endereço se não existia
     */
    public static Address updateAddress(Address newAddress, Address existingAddress) {
        // Se nenhum endereço novo foi fornecido, retorna o existente sem alterações
        if (newAddress == null) {
            return existingAddress;
        }

        // Se não existe endereço atual e um novo foi fornecido, retorna o novo
        if (existingAddress == null) {
            return newAddress;
        }

        // Atualizar endereço existente (apenas campos não-null)
        if (newAddress.getStreet() != null) {
            existingAddress.setStreet(newAddress.getStreet());
        }
        if (newAddress.getNumber() != null) {
            existingAddress.setNumber(newAddress.getNumber());
        }
        if (newAddress.getComplement() != null) {
            existingAddress.setComplement(newAddress.getComplement());
        }
        if (newAddress.getNeighborhood() != null) {
            existingAddress.setNeighborhood(newAddress.getNeighborhood());
        }
        if (newAddress.getCity() != null) {
            existingAddress.setCity(newAddress.getCity());
        }
        if (newAddress.getState() != null) {
            existingAddress.setState(newAddress.getState());
        }
        if (newAddress.getZipCode() != null) {
            existingAddress.setZipCode(newAddress.getZipCode());
        }

        return existingAddress;
    }
}
