package com.scheduly.api.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public static boolean isValidCpf(String cpf) {
        // Remove caracteres não numéricos
        var cleanCpf = cpf.replaceAll("[^0-9]", "");

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
