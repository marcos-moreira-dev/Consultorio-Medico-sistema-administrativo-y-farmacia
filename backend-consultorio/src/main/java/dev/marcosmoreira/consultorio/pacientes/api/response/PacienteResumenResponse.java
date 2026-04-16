package dev.marcosmoreira.consultorio.pacientes.api.response;

import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import java.time.LocalDate;

/**
 * DTO de salida resumido para listados de pacientes.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PacienteResumenResponse {

    private Long pacienteId;
    private String nombreCompleto;
    private String telefono;
    private String cedula;
    private LocalDate fechaNacimiento;

    /**
     * Constructor vacío requerido por serialización.
     */
    public PacienteResumenResponse() {
    }

    /**
     * Construye la respuesta resumida.
     *
     * @param pacienteId identificador del paciente
     * @param nombreCompleto nombre completo derivado
     * @param telefono teléfono
     * @param cedula cédula
     * @param fechaNacimiento fecha de nacimiento
     */
    public PacienteResumenResponse(
            Long pacienteId,
            String nombreCompleto,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento
    ) {
        this.pacienteId = pacienteId;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Construye la respuesta resumida a partir del dominio.
     *
     * @param paciente paciente del dominio
     * @return DTO resumido
     */
    public static PacienteResumenResponse fromDomain(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }

        return new PacienteResumenResponse(
                paciente.getPacienteId(),
                paciente.getNombreCompleto(),
                paciente.getTelefono(),
                paciente.getCedula(),
                paciente.getFechaNacimiento()
        );
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
