package dev.marcosmoreira.consultorio.roles.domain.enums;

import java.util.Arrays;

public enum CodigoRol {
    ADMIN_CONSULTORIO("ADMIN_CONSULTORIO"),
    OPERADOR_CONSULTORIO("OPERADOR_CONSULTORIO"),
    PROFESIONAL_CONSULTORIO("PROFESIONAL_CONSULTORIO");

    private final String value;
    CodigoRol(String value) { this.value = value; }
    public String getValue() { return value; }
    public static CodigoRol fromValue(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("El código de rol es obligatorio.");
        return Arrays.stream(values()).filter(item -> item.value.equalsIgnoreCase(value.trim())).findFirst().orElseThrow(() -> new IllegalArgumentException("No existe un código de rol reconocido para el valor: " + value));
    }
}
