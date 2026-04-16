package dev.marcosmoreira.consultorio.pacientes.api.response;

import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de salida con el detalle completo de un paciente.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PacienteResponse {

    private Long pacienteId;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    private String telefono;
    private String cedula;
    private LocalDate fechaNacimiento;
    private String direccionBasica;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por serialización.
     */
    public PacienteResponse() {
    }

    /**
     * Construye la respuesta completa.
     *
     * @param pacienteId identificador del paciente
     * @param nombres nombres del paciente
     * @param apellidos apellidos del paciente
     * @param nombreCompleto nombre completo derivado
     * @param telefono teléfono
     * @param cedula cédula
     * @param fechaNacimiento fecha de nacimiento
     * @param direccionBasica dirección básica
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public PacienteResponse(
            Long pacienteId,
            String nombres,
            String apellidos,
            String nombreCompleto,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.pacienteId = pacienteId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.direccionBasica = direccionBasica;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Construye la respuesta a partir del dominio.
     *
     * @param paciente paciente del dominio
     * @return DTO listo para serializar
     */
    public static PacienteResponse fromDomain(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }

        return new PacienteResponse(
                paciente.getPacienteId(),
                paciente.getNombres(),
                paciente.getApellidos(),
                paciente.getNombreCompleto(),
                paciente.getTelefono(),
                paciente.getCedula(),
                paciente.getFechaNacimiento(),
                paciente.getDireccionBasica(),
                paciente.getFechaCreacion(),
                paciente.getFechaActualizacion()
        );
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
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getDireccionBasica() {
        return direccionBasica;
    }

    public void setDireccionBasica(String direccionBasica) {
        this.direccionBasica = direccionBasica;
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
}
