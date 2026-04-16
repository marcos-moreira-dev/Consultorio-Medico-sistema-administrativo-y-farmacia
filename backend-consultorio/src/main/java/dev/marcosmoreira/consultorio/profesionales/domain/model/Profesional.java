package dev.marcosmoreira.consultorio.profesionales.domain.model;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa a un profesional clínico del consultorio.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class Profesional {

    private Long profesionalId;
    private Long usuarioId;
    private String nombres;
    private String apellidos;
    private String especialidadBreve;
    private String registroProfesional;
    private EstadoProfesional estadoProfesional;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío.
     */
    public Profesional() {
    }

    /**
     * Construye un profesional completo.
     *
     * @param profesionalId identificador del profesional
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional
     * @param estadoProfesional estado lógico
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public Profesional(
            Long profesionalId,
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional,
            EstadoProfesional estadoProfesional,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.profesionalId = profesionalId;
        this.usuarioId = usuarioId;
        this.nombres = normalizeNullableText(nombres);
        this.apellidos = normalizeNullableText(apellidos);
        this.especialidadBreve = normalizeNullableText(especialidadBreve);
        this.registroProfesional = normalizeNullableText(registroProfesional);
        this.estadoProfesional = estadoProfesional;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Long profesionalId) {
        this.profesionalId = profesionalId;
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

    public EstadoProfesional getEstadoProfesional() {
        return estadoProfesional;
    }

    public void setEstadoProfesional(EstadoProfesional estadoProfesional) {
        this.estadoProfesional = estadoProfesional;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Devuelve el nombre completo derivado a partir de nombres y apellidos.
     *
     * @return nombre completo o {@code null} si no hay información útil
     */
    public String getNombreCompleto() {
        String n = nombres == null ? "" : nombres.trim();
        String a = apellidos == null ? "" : apellidos.trim();
        String full = (n + " " + a).trim();
        return full.isEmpty() ? null : full;
    }

    /**
     * Indica si el profesional está activo.
     *
     * @return {@code true} si el estado es ACTIVO; {@code false} en otro caso
     */
    public boolean isActivo() {
        return estadoProfesional != null && estadoProfesional.isOperativo();
    }

    /**
     * Indica si existe usuario asociado.
     *
     * @return {@code true} si existe usuario asociado; {@code false} en caso contrario
     */
    public boolean hasUsuarioAsociado() {
        return usuarioId != null;
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
