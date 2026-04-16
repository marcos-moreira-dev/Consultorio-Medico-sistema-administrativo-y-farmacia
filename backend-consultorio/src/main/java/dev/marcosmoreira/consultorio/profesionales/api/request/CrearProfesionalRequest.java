package dev.marcosmoreira.consultorio.profesionales.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear un profesional del consultorio.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CrearProfesionalRequest {

    @Positive(message = "Si se envía usuarioId, debe ser mayor que cero.")
    private Long usuarioId;

    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(max = 100, message = "Los nombres no pueden exceder los 100 caracteres.")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(max = 100, message = "Los apellidos no pueden exceder los 100 caracteres.")
    private String apellidos;

    @Size(max = 120, message = "La especialidad breve no puede exceder los 120 caracteres.")
    private String especialidadBreve;

    @Size(max = 50, message = "El registro profesional no puede exceder los 50 caracteres.")
    private String registroProfesional;

    /**
     * Constructor vacío requerido por serialización.
     */
    public CrearProfesionalRequest() {
    }

    /**
     * Construye el request completo de creación.
     *
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional, si aplica
     */
    public CrearProfesionalRequest(
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional
    ) {
        this.usuarioId = usuarioId;
        this.nombres = normalizeNullableText(nombres);
        this.apellidos = normalizeNullableText(apellidos);
        this.especialidadBreve = normalizeNullableText(especialidadBreve);
        this.registroProfesional = normalizeNullableText(registroProfesional);
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = normalizeNullableText(nombres);
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = normalizeNullableText(apellidos);
    }

    public String getEspecialidadBreve() {
        return especialidadBreve;
    }

    public void setEspecialidadBreve(String especialidadBreve) {
        this.especialidadBreve = normalizeNullableText(especialidadBreve);
    }

    public String getRegistroProfesional() {
        return registroProfesional;
    }

    public void setRegistroProfesional(String registroProfesional) {
        this.registroProfesional = normalizeNullableText(registroProfesional);
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto con trim aplicado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
