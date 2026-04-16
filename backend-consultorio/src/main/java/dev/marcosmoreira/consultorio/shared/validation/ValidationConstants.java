package dev.marcosmoreira.consultorio.shared.validation;

/**
 * Constantes de validación reutilizables en distintos módulos del backend.
 *
 * <p>No pretende reemplazar las anotaciones de Bean Validation, sino centralizar
 * tamaños y límites frecuentes para reducir números mágicos dispersos.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public final class ValidationConstants {

    public static final int MAX_USERNAME_LENGTH = 100;
    public static final int MAX_PASSWORD_LENGTH = 255;
    public static final int MAX_NOMBRE_LENGTH = 150;
    public static final int MAX_DESCRIPCION_BREVE_LENGTH = 500;
    public static final int MAX_OBSERVACION_LENGTH = 1000;

    private ValidationConstants() {
    }
}
