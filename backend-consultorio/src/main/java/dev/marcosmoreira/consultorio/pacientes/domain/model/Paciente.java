package dev.marcosmoreira.consultorio.pacientes.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa a un paciente del consultorio.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class Paciente {

    private Long pacienteId;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String cedula;
    private LocalDate fechaNacimiento;
    private String direccionBasica;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío.
     */
    public Paciente() {
    }

    /**
     * Construye un paciente completo.
     *
     * @param pacienteId identificador del paciente
     * @param nombres nombres del paciente
     * @param apellidos apellidos del paciente
     * @param telefono teléfono
     * @param cedula cédula
     * @param fechaNacimiento fecha de nacimiento
     * @param direccionBasica dirección básica
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public Paciente(
            Long pacienteId,
            String nombres,
            String apellidos,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.pacienteId = pacienteId;
        this.nombres = normalizeNullableText(nombres);
        this.apellidos = normalizeNullableText(apellidos);
        this.telefono = normalizeNullableText(telefono);
        this.cedula = normalizeNullableText(cedula);
        this.fechaNacimiento = fechaNacimiento;
        this.direccionBasica = normalizeNullableText(direccionBasica);
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = normalizeNullableText(telefono);
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = normalizeNullableText(cedula);
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccionBasica() {
        return direccionBasica;
    }

    public void setDireccionBasica(String direccionBasica) {
        this.direccionBasica = normalizeNullableText(direccionBasica);
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
     * Indica si el paciente tiene cédula informada.
     *
     * @return {@code true} si existe cédula útil; {@code false} en caso contrario
     */
    public boolean hasCedula() {
        return cedula != null && !cedula.isBlank();
    }

    /**
     * Indica si el paciente tiene teléfono informado.
     *
     * @return {@code true} si existe teléfono útil; {@code false} en caso contrario
     */
    public boolean hasTelefono() {
        return telefono != null && !telefono.isBlank();
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
