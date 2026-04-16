package dev.marcosmoreira.consultorio.pacientes.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO de entrada para actualizar un paciente.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ActualizarPacienteRequest {

    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(max = 100, message = "Los nombres no pueden exceder los 100 caracteres.")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(max = 100, message = "Los apellidos no pueden exceder los 100 caracteres.")
    private String apellidos;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres.")
    private String telefono;

    @Size(max = 20, message = "La cédula no puede exceder los 20 caracteres.")
    private String cedula;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @Size(max = 200, message = "La dirección básica no puede exceder los 200 caracteres.")
    private String direccionBasica;

    /**
     * Constructor vacío requerido por serialización.
     */
    public ActualizarPacienteRequest() {
    }

    /**
     * Construye el request completo.
     *
     * @param nombres nombres del paciente
     * @param apellidos apellidos del paciente
     * @param telefono teléfono, si existe
     * @param cedula cédula, si existe
     * @param fechaNacimiento fecha de nacimiento, si existe
     * @param direccionBasica dirección básica, si existe
     */
    public ActualizarPacienteRequest(
            String nombres,
            String apellidos,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica
    ) {
        this.nombres = normalizeNullableText(nombres);
        this.apellidos = normalizeNullableText(apellidos);
        this.telefono = normalizeNullableText(telefono);
        this.cedula = normalizeNullableText(cedula);
        this.fechaNacimiento = fechaNacimiento;
        this.direccionBasica = normalizeNullableText(direccionBasica);
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
