package dev.marcosmoreira.consultorio.shared.validation;

/**
 * Mensajes de validación reutilizables.
 *
 * <p>Centralizar mensajes básicos puede ayudar a mantener coherencia textual
 * cuando varias partes del sistema validan conceptos parecidos.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public final class ValidationMessages {

    public static final String REQUIRED_FIELD = "Este campo es obligatorio.";
    public static final String POSITIVE_ID = "El identificador debe ser mayor que cero.";
    public static final String INVALID_DATE_RANGE = "La fecha inicial no puede ser posterior a la fecha final.";
    public static final String INVALID_EMAIL = "El correo electrónico no tiene un formato válido.";
    public static final String INVALID_USERNAME = "El username enviado no es válido.";

    private ValidationMessages() {
    }
}
